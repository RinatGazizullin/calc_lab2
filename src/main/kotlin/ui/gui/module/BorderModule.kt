package ui.gui.module

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import core.basic.Subscriber
import ui.gui.processor.ApplicationProcessor.Companion.PADDING_ROUND
import ui.gui.element.input.BorderInput
import ui.gui.element.input.EpsilonInput
import ui.gui.element.input.TokenInput
import ui.gui.processor.StateManager

class BorderModule(
    stateManager: StateManager,
) : Subscriber {
    private val first = TokenInput(stateManager, 0)
    private val second = TokenInput(stateManager, 1)

    private val leftFirst = BorderInput(stateManager, 0, true, "Левая граница")
    private val rightFirst = BorderInput(stateManager, 0, false, "Правая граница")

    private val leftSecond = BorderInput(stateManager, 1, true, "Левая граница")
    private val rightSecond = BorderInput(stateManager, 1, false, "Правая граница")

    private val epsilonInput = EpsilonInput(stateManager)
    private val redrawTrigger = mutableStateOf(true)

    init {
        stateManager.subscribe(this)
    }

    override fun changed() {
        redrawTrigger.value = !redrawTrigger.value
    }

    @Composable
    fun content() {
        redrawTrigger.value

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(PADDING_ROUND),
            verticalArrangement = Arrangement.spacedBy(PADDING_ROUND)
        ) {
            Row(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(PADDING_ROUND)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(PADDING_ROUND)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        first.content()
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        leftFirst.content()
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        rightFirst.content()
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(PADDING_ROUND)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        second.content()
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        leftSecond.content()
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        rightSecond.content()
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                epsilonInput.content()
            }
        }
    }
}
