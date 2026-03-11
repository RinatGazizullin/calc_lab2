package ui.gui.module

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.sp
import ui.gui.processor.ApplicationProcessor.Companion.PADDING_ROUND

abstract class Button(
    private val color: Color,
    private val text: String,
) {
    abstract fun execute()

    @Composable
    fun content() {
        var isHovered by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { execute() },
                modifier = Modifier
                    .fillMaxSize()
                    .scale(if (isHovered) 1.05f else 1f)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                isHovered = event.changes.any { it.pressed }
                            }
                        }
                    },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = color,
                    contentColor = Color.White
                ),
                elevation = null,
                shape = RoundedCornerShape(PADDING_ROUND)
            ) {
                Text(
                    text = text,
                    fontSize = 14.sp
                )
            }
        }
    }
}
