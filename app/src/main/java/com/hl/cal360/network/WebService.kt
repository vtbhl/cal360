package com.hl.cal360.network

import android.os.AsyncTask
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


/**
 * A product entry in the list of products.
 */



class WebService(var path:String): AsyncTask<Void, Void, String>(){

    lateinit var url: URL
    //var path = "https://jsonplaceholder.typicode.com/posts"
    val TAG = "SmartTime"
    lateinit var response: StringBuffer
    lateinit var responseText: String

    override fun doInBackground(vararg p0: Void?): String? {
        return getData(path)
    }

    fun getData(path:String):String? {

        try {
            // viet giong het Java thuong :)), hay day.
            url = URL(path)
            Log.d(TAG, "ServerData: $path")
            val conn = url.openConnection() as HttpURLConnection
            //conn.setReadTimeout(15000);
            //conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET")

            val responseCode = conn.getResponseCode()
            response = StringBuffer()
            Log.d(TAG, "Response code: $responseCode")
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                // Reading response from input Stream
                val inStream = BufferedReader(
                    InputStreamReader(conn.getInputStream())
                )
                var output =inStream.readLine()

                while (output != null) {
                    response.append(output)
                    output = inStream.readLine()
                }
                inStream.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        responseText = response.toString()
        //  response = service.ServerData(path, postDataParams);
        Log.d(TAG, "data:$responseText")
        val location = convertToLocation(responseText)
        return "Result is: Lat : " + location?.lat + ", Lng: " + location?.lng
    }

    fun convertToLocation (responseText:String): Location? {
         var locationSearch: Location? = null
        try {
            val jsonarray = JSONArray("[" + responseText + "]")
            for (i in 0 until jsonarray.length()) {
                val jsonobject = jsonarray.getJSONObject(i)
                val candidates = jsonobject.getString("candidates")
                val canArray = JSONArray(candidates)
                for (j in 0 until canArray.length()) {
                    val calObject = canArray.getJSONObject(j)
                    val geometry = "[" + calObject.getString("geometry") +"]"
                    val geoArray = JSONArray(geometry)
                    for (k in 0 until geoArray.length()) {
                        val geoObject = geoArray.getJSONObject(k)
                        val location = "[" + geoObject.getString("location") +"]"
                        val locArray = JSONArray(location)
                        for (l in 0 until locArray.length()) {
                            val locObject = locArray.getJSONObject(l)
                            locationSearch = Location(locObject.getDouble("lat"), locObject.getDouble("lng"))
                        }
                    }

                }
                //Country countryObj=new Country(id,country);
                //countries.add(countryObj);
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return locationSearch
    }
    //fun postData
}