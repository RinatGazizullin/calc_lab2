package ui.gui.element.input

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import core.basic.Subscriber
import ui.gui.processor.ApplicationProcessor

abstract class DataInput(private val placeholder: String) : Subscriber {
    abstract val state: MutableState<String>
    val trigger = mutableStateOf(true)

    abstract fun execData(value: String)

    @Composable
    fun content(
        modifier: Modifier = Modifier
    ) {
        /*
        val focusManager = LocalFocusManager.current
        var hadFocus by remember { mutableStateOf(false) }
         */
        OutlinedTextField(
            value = state.value,
            onValueChange = { newValue ->
                state.value = newValue
                execData(newValue)
            },
            placeholder = {
                androidx.compose.material.Text(
                    text = placeholder,
                    color = Color.Gray
                )
            },
            modifier = modifier.fillMaxSize()
                /*
                .onFocusChanged { focusState ->
                    if (hadFocus && !focusState.isFocused) {
                        execFocus(state.value)
                    }
                }*/,
            shape = RoundedCornerShape(ApplicationProcessor.PADDING_ROUND),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                backgroundColor = Color.White
            ),
            singleLine = true /*,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )*/
        )
    }
}
