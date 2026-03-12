package ui.gui.element.input

import androidx.compose.runtime.mutableStateOf
import core.basic.Subscriber
import ui.gui.processor.StateManager

class EpsilonInput(
    private val stateManager: StateManager
) : DataInput("Погрешность"), Subscriber {
    override var state = mutableStateOf(stateManager.epsilon)

    init {
        stateManager.subscribe(this)
    }

    override fun changed() {
        state.value = stateManager.epsilon
        trigger.value = !trigger.value
    }

    override fun execData(value: String) {
        stateManager.epsilon = value
    }
}
