package ui.gui.element

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import core.basic.Subscriber
import core.processor.ExpressionProcessor
import ui.gui.processor.ApplicationProcessor
import ui.gui.processor.GraphicProcessor

class Input (
    private val expressionProcessor: ExpressionProcessor,
    private val graphicProcessor: GraphicProcessor,
    private val index: Int
) : Subscriber {
    private var state: MutableState<String>? = null

    init {
        expressionProcessor.subscribe(this)
    }

    @Composable
    fun content(
        onValueChange: (String) -> Unit = {},
        modifier: Modifier = Modifier
    ) {
        val localTextState = remember { mutableStateOf(expressionProcessor.exps[index].body) }
        state = localTextState

        OutlinedTextField(
            value = localTextState.value,
            onValueChange = { newValue ->
                localTextState.value = newValue
                onValueChange(newValue)
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

    override fun changed() {
        state?.value = expressionProcessor.exps[index].body
    }
}
