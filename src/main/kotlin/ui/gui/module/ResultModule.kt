package ui.gui.module

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ui.gui.element.Output
import ui.gui.element.ShowAll
import ui.gui.element.SolveAll
import ui.gui.processor.ApplicationProcessor.Companion.PADDING_ROUND

class ResultModule {
    private val solve = SolveAll()
    private val show = ShowAll()
    private val data = Output()

    @Composable
    fun content() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(PADDING_ROUND),
            verticalArrangement = Arrangement.spacedBy(PADDING_ROUND)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                // .background(Color.DarkGray)
            ) {
                data.content()
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(PADDING_ROUND)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    solve.content()
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    show.content()
                }
            }
        }
    }
}
