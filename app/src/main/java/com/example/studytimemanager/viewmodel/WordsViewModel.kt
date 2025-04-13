package com.example.studytimemanager.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class WordsViewModel : ViewModel() {

    private val _word = MutableStateFlow("Loading...")
    private val _description = MutableStateFlow("Please wait...")
    private val _isLoading = MutableStateFlow(false)

    val word = _word.asStateFlow()
    val description = _description.asStateFlow()
    val isLoading = _isLoading.asStateFlow()

    private val client = OkHttpClient()

    private val apiKey = "fd731874cemsh8cab46b7d6ddcf4p1535a4jsn9a3dfa1df8fe"

    fun fetchWordData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _isLoading.update { true }

                val request = Request.Builder()
                    .url("https://wordsapiv1.p.rapidapi.com/words/?random=true")
                    .get()
                    .addHeader("x-rapidapi-key", apiKey)
                    .addHeader("x-rapidapi-host", "wordsapiv1.p.rapidapi.com")
                    .build()

                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        println("Request failed with code: ${response.code}")
                        return@use
                    }

                    val json = JSONObject(response.body!!.string())
                    val wordStr = json.getString("word")
                    val results = json.optJSONArray("results")
                    val definitionStr = if (results != null && results.length() > 0) {
                        results.getJSONObject(0).optString("definition", "No definition")
                    } else {
                        "No definition available"
                    }

                    _word.update { wordStr }
                    _description.update { definitionStr }
                }
            } catch (e: Exception) {
                println("Exception in fetchWordData: ${e.message}")
                _word.update { "Error" }
                _description.update { "Network error occurred" }
            } finally {
                _isLoading.update { false }
            }
        }
    }
}