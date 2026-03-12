package ui.gui.processor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import core.processor.ExpressionProcessor
import ui.gui.module.InfoModule
import ui.gui.module.InstrumentModule
import java.awt.Toolkit

class ApplicationProcessor(
    expressionProcessor: ExpressionProcessor,
    graphicProcessor: GraphicProcessor
) {
    private val instrumentModule = InstrumentModule(graphicProcessor, StateManager(expressionProcessor)
    )
    private val infoModule = InfoModule(expressionProcessor, graphicProcessor)

    companion object {
        val PADDING_ROUND = 10.dp
    }

    fun showWindow() {
        val screenSize = Toolkit.getDefaultToolkit().screenSize

        application {
            Window(
                resizable = false,
                title = "Калькулятор уравнений",
                icon = painterResource("icon.png"),
                onCloseRequest = ::exitApplication,
                state = rememberWindowState(
                    width = (screenSize.width * .8).toInt().dp,
                    height = (screenSize.height * .6).toInt().dp
                )
            ) {
                MaterialTheme {
                    mainLayout()
                }
            }
        }
    }

    @Composable
    private fun mainLayout() {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(PADDING_ROUND),
                contentAlignment = Alignment.Center
            ) {
                instrumentModule.content()
            }
            /*
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .fillMaxHeight()
                    .background(Color.LightGray)
            )
             */
            Box(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
                    .background(Color.White)
                    .padding(PADDING_ROUND),
                contentAlignment = Alignment.Center
            ) {
                infoModule.content()
            }
        }
    }
}
