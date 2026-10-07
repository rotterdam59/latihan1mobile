package com.example.ezemkofi.connection

import android.R.attr.password
import android.util.Log.e
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

class Connection {



    suspend fun loginUser(urlString: String, username: String, password: String): String? {
        return withContext(Dispatchers.IO)

        //block token
        {
            var urlConnection: HttpURLConnection? = null
            try {
                val url =URL(urlString)
                urlConnection = url.openConnection() as HttpURLConnection


                urlConnection.requestMethod = "POST"
                urlConnection.connectTimeout = 5000
                urlConnection.readTimeout = 5000
                urlConnection.doOutput = true

                urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
                urlConnection.setRequestProperty("accept", "application/json")


                val jsonInputString = "{\"username\":\"$username\",\"password\":\"$password\"}"

                val os = urlConnection.outputStream
                val writer = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                writer.write(jsonInputString)
                writer.flush()
                writer.close()
                os.close()


                if(urlConnection.responseCode == HttpURLConnection.HTTP_OK){
                    val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    return@withContext reader.use{ it.readText() }
                }
            }
            catch (e: Exception) {
                android.util.Log.e("DEBUG_LOGIN", "Gagal karena: ${e.message}")
                e.printStackTrace()
            }  finally {
                urlConnection?.disconnect()
            }
            return@withContext null

        }
    }

    suspend fun getMe(urlString: String, respon: String?): String? {
        return withContext(Dispatchers.IO)
        {
                var urlConnectionG: HttpURLConnection? = null
            try {
                val url = URL(urlString)
                urlConnectionG = url.openConnection() as HttpURLConnection

                urlConnectionG.requestMethod = "GET"
                urlConnectionG.connectTimeout = 2000
                urlConnectionG.readTimeout = 2000

                urlConnectionG.setRequestProperty("Authorization", "Bearer $respon")
                urlConnectionG.setRequestProperty("accept", "application/json")

                val jsonInputString = "{\"respon\":\"$respon\"}"

                if (urlConnectionG.responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(urlConnectionG.inputStream))
                //  val responJson = reader.use { it.readText() }
                    return@withContext reader.use { it.readText() }

//                    val userInformationJson = JSONObject(responJson)
//                    return@withContext userInformationJson.getString("respon")


                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                urlConnectionG?.disconnect()
            }
                return@withContext null

            }
        }
}





