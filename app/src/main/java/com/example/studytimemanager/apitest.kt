package com.example.studytimemanager

import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

fun main() = runBlocking {
    testQuoteApi()
}

suspend fun testQuoteApi() {
    withContext(Dispatchers.IO) {
        try {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://famous-quotes4.p.rapidapi.com/random?category=all&count=1")
                .get()
                .addHeader("X-RapidAPI-Key", "fd731874cemsh8cab46b7d6ddcf4p1535a4jsn9a3dfa1df8fe")
                .addHeader("X-RapidAPI-Host", "famous-quotes4.p.rapidapi.com")
                .build()

            val response = client.newCall(request).execute()
            val body = response.body?.string()

            println("Success: ${response.isSuccessful}")
            println("Code: ${response.code}")
            println("Body: $body")

            if (response.isSuccessful && body != null) {
                val json = JSONObject(body)
                val quote = json.getString("content")
                val author = json.getJSONObject("originator").getString("name")
                println("Quote: $quote")
                println("Author: $author")
            }

        } catch (e: Exception) {
            println("Exception: ${e.localizedMessage}")
        }
    }
}