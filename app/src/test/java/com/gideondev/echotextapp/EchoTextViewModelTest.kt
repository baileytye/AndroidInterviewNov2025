package com.gideondev.echotextapp

import com.gideondev.echotextapp.viewmodel.EchoTextViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class EchoTextViewModelTest {
private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when user input text, display function is called, uiState should contain the inputted value `()  {
        val expected: EchoTextViewModel.UiState = EchoTextViewModel.UiState(inputtedText = "Input value", error = null)

        val viewModel = createViewModel()
        viewModel.echoText("Input value")

        assertEquals(viewModel.uiState.value, expected)
    }

    @Test
    fun `when no text is inputted, submit button click, uiState should contain error `()  {
        val expected: EchoTextViewModel.UiState = EchoTextViewModel.UiState(inputtedText = "", error = "Empty Text field. Please input text in the text field")

        val viewModel = createViewModel()
        viewModel.echoText("")

        assertEquals(viewModel.uiState.value, expected)
    }

    @Test
    fun `when there is error, error is displayed and clear error function called, error variable in uiState should reset to null `() {
        val expected: EchoTextViewModel.UiState = EchoTextViewModel.UiState(inputtedText = "", error = null)

        val viewModel = createViewModel()
        viewModel.echoText("")

        viewModel.clearError()

        assertEquals(viewModel.uiState.value, expected)
    }

    @Test
    fun `when user input text, submit button is click and text is displayed , inputtedValue in UiState should be empty after reset method been called `() {
        val expected: EchoTextViewModel.UiState = EchoTextViewModel.UiState(inputtedText = "", error = null)

        val viewModel = createViewModel()
        viewModel.echoText("Input value")

        viewModel.resetUserInputtedText()

        assertEquals(viewModel.uiState.value, expected)
    }

    private fun createViewModel() = EchoTextViewModel()
}