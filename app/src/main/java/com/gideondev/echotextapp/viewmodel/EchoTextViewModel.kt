package com.gideondev.echotextapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class EchoTextViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value)

    /**
     * This function validate user inputted text and update the UiState.
     * If the param @value is empty, uiState will be update to contain error.
     * If the  param @value is not empty,uiState value is updated to contain text to display on view
     * by updating the inputtedText variable.
     */
    fun echoText(value: String) {
        if (value.isEmpty()) {
            _uiState.update {
                it.copy(
                    error = "Empty Text field. Please input text in the text field"
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    inputtedText = value
                )
            }
        }
    }

    /**
     * This function is used update error variable in the uiState to default after error is display
     * on the view
     */
    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }

    /**
     * This function is used update inputtedText variable in the uiState to default after the inputted
     * is displayed on the view
     */
    fun resetUserInputtedText() {
        _uiState.update { it.copy(inputtedText = "") }
    }

    data class UiState(
        val error: String? = null,
        val inputtedText: String = ""
    )
}