package ui.gui.element.data

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import core.basic.Subscriber
import ui.gui.processor.ApplicationProcessor
import ui.gui.processor.StateManager

class Output(
    private val stateManager: StateManager
) : Subscriber {
    private val value = mutableStateOf(stateManager.outputData)
    private val trigger = mutableStateOf(false)

    init {
        stateManager.subscribe(this)
    }

    override fun changed() {
        value.value = stateManager.outputData
        trigger.value = !trigger.value
    }

    @Composable
    fun content(
        modifier: Modifier = Modifier,
        placeholder: String = "Результат..."
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(ApplicationProcessor.PADDING_ROUND)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (value.value.isNotEmpty()) {
                Text(
                    text = value.value,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            } else {
                Text(
                    text = placeholder,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
