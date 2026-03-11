package ui.gui.module

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

class GraphModule {
    @Composable
    fun content() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFF5722)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "График",
                color = Color.Green
            )
        }
    }
}
