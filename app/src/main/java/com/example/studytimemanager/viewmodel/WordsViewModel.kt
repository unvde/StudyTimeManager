package com.example.studytimemanager.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class WordsViewModel : ViewModel() {

    private val _word = MutableStateFlow("Loading...")
    val word: StateFlow<String> get() = _word

    private val _description = MutableStateFlow("Loading description...")
    val description: StateFlow<String> get() = _description

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val client = OkHttpClient()

    fun fetchWordData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val word: String
                val description: String

                withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://wordsapiv1.p.rapidapi.com/words/?random=true")
                        .get()
                        .addHeader("x-rapidapi-key", "fd731874cemsh8cab46b7d6ddcf4p1535a4jsn9a3dfa1df8fe")
                        .addHeader("x-rapidapi-host", "wordsapiv1.p.rapidapi.com")
                        .build()

                    val response = client.newCall(request).execute()
                    val responseBody = response.body?.string()

                    if (!response.isSuccessful || responseBody == null) {
                        throw Exception("Response error: $responseBody")
                    }

                    val json = JSONObject(responseBody)
                    word = json.getString("word")
                    val results = json.optJSONArray("results")
                    description = if (results != null && results.length() > 0) {
                        results.getJSONObject(0).optString("definition", "No definition")
                    } else {
                        "No definition available"
                    }
                }

                _word.value = word
                _description.value = description

            } catch (e: Exception) {
                Log.e("WordsViewModel", "Exception: ${e.message}", e)
                _word.value = "Exception"
                _description.value = e.localizedMessage ?: "Unknown exception"
            } finally {
                _isLoading.value = false
            }
        }
    }
}