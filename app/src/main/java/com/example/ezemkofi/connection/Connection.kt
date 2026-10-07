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
                urlConnection.connectTimeout = 10000
                urlConnection.readTimeout = 10000
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
                e.printStackTrace()
            }  finally {
                urlConnection?.disconnect()
            }
            return@withContext null

        }
    }

    suspend fun getMe(urlString: String, respon: String): String? {
        return withContext(Dispatchers.IO)
        {
            try {
                var urlConnection: HttpURLConnection? = null
                val url = URL(urlString)
                urlConnection = url.openConnection() as HttpURLConnection

                urlConnection.requestMethod = "GET"
                urlConnection.connectTimeout = 10000
                urlConnection.readTimeout = 10000
                urlConnection.doOutput = true


                urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
                urlConnection.setRequestProperty("Authorization", "Bearer $respon")

                val jsonInputString = "{\"respon\":\"$respon\"}"

                val os = urlConnection.outputStream
                val writer = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
                writer.write(jsonInputString)
                writer.flush()
                writer.close()
                os.close()


                if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                    return@withContext reader.use { it.readText() }
                }
                catch(e: Exception) {
                    e.printStackTrace()
                } finally {
                    urlConnection?.disconnect()
                }
                return@withContext null

            }
        }
    }


}


//package com.example.ezemkofi.connection
//
//import android.util.Log
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import org.json.JSONObject
//import java.net.HttpURLConnection
//import java.net.URL
//
//class Connection {
//
//    // POST /api/auth -> token teks biasa, atau null kalau gagal
//    suspend fun loginUser(urlString: String, username: String, password: String): String? {
//        val requestBodyJson = JSONObject()
//            .put("username", username)
//            .put("password", password)
//            .toString()
//        return sendRequest(urlString, "POST", requestBodyJson, null)
//    }
//
//    // POST /api/register -> token teks biasa, atau null kalau gagal
//    suspend fun registerUser(
//        urlString: String,
//        username: String,
//        fullName: String,
//        email: String,
//        password: String
//    ): String? {
//        val requestBodyJson = JSONObject()
//            .put("username", username)
//            .put("fullname", fullName)   // di API huruf kecil semua
//            .put("email", email)
//            .put("password", password)
//            .toString()
//        return sendRequest(urlString, "POST", requestBodyJson, null)
//    }
//
//    // GET /api/me -> JSON data user yang sedang login, atau null kalau gagal
//    suspend fun getCurrentUser(urlString: String, token: String): String? {
//        return sendRequest(urlString, "GET", null, token)
//    }
//
//    // Satu fungsi untuk semua request.
//    // requestBodyJson = null berarti tanpa body (GET). token = null berarti tanpa header Authorization.
//    private suspend fun sendRequest(
//        urlString: String,
//        httpMethod: String,
//        requestBodyJson: String?,
//        token: String?
//    ): String? {
//        return withContext(Dispatchers.IO) {
//            var urlConnection: HttpURLConnection? = null
//            try {
//                urlConnection = URL(urlString).openConnection() as HttpURLConnection
//                urlConnection.requestMethod = httpMethod
//                urlConnection.connectTimeout = 10000
//                urlConnection.readTimeout = 10000
//                urlConnection.setRequestProperty("Accept", "*/*")
//
//                if (token != null) {
//                    urlConnection.setRequestProperty("Authorization", "Bearer $token")
//                }
//
//                if (requestBodyJson != null) {
//                    // doOutput hanya boleh true kalau ada body. Kalau tidak, GET diam-diam berubah jadi POST.
//                    urlConnection.doOutput = true
//                    urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
//                    urlConnection.outputStream.use { outputStream ->
//                        outputStream.write(requestBodyJson.toByteArray(Charsets.UTF_8))
//                    }
//                }
//
//                val responseCode = urlConnection.responseCode
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    return@withContext urlConnection.inputStream.bufferedReader().use { it.readText() }
//                }
//
//                val errorBody = urlConnection.errorStream?.bufferedReader()?.use { it.readText() }
//                Log.e("API", "Server menolak. Kode: $responseCode, isi: $errorBody")
//            } catch (exception: Exception) {
//                Log.e("API", "Request gagal", exception)
//            } finally {
//                urlConnection?.disconnect()
//            }
//            null
//        }
//    }
//}