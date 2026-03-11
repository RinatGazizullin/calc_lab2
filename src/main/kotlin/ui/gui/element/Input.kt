package ui.gui.element

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import core.processor.ExpressionProcessor
import ui.gui.processor.ApplicationProcessor
import ui.gui.processor.GraphicProcessor

class Input(
    private val expressionProcessor: ExpressionProcessor,
    private val graphicProcessor: GraphicProcessor,
    private val index: Int
) {
    @Composable
    fun content(
        value: String = expressionProcessor.exps[index].body,
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