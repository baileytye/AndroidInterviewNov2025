package com.gideondev.echotextapp.validation

import android.util.Log
import kotlinx.coroutines.delay
import java.util.*

class TextValidator {

    companion object {
        var instance: TextValidator? = null
        fun getInstance(): TextValidator {
            if (instance == null) {
                instance = TextValidator()
            }
            return instance!!
        }
    }

    private val bannedWords = mutableListOf("spam", "test", "admin", "root")
    private var validationCount = 0

    suspend fun validateText(text: String): String? {
        delay(50)
        validationCount++
        Log.d("TextValidator", "Validation #$validationCount for: $text")

        if (text.isEmpty()) {
            return "Empty Text field. Please input text in the text field"
        }

        if (text.length < 3) {
            return "Text too short"
        }

        if (text.length > 100) {
            return "Text too long"
        }

        if (text.contains("@") || text.contains("#") || text.contains("$")) {
            return "Special characters not allowed"
        }

        for (word in bannedWords) {
            if (text.lowercase().contains(word)) {
                return "Content contains prohibited words: $word"
            }
        }

        if (text.trim() != text) {
            return "Text has leading or trailing spaces"
        }

        val calendar = Calendar.getInstance()
        if (calendar.get(Calendar.HOUR_OF_DAY) < 9 || calendar.get(Calendar.HOUR_OF_DAY) > 17) {
            return "Text submission only allowed during business hours (9 AM - 5 PM)"
        }

        return null
    }

    fun getValidationCount(): Int {
        return validationCount
    }
}