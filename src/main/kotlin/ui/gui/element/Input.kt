package ui.gui.element

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.background
import ui.gui.processor.ApplicationProcessor

class Input {
    @Composable
    fun content(
        value: String = "",
        onValueChange: (String) -> Unit = {},
        modifier: Modifier = Modifier
    ) {
        var text by remember { mutableStateOf(value) }

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onValueChange(it)
            },
            modifier = modifier.fillMaxSize(),
            shape = RoundedCornerShape(ApplicationProcessor.PADDING_ROUND),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                backgroundColor = Color.White
            ),
            singleLine = true
        )
    }
}