package ui.gui.element.input

import androidx.compose.runtime.mutableStateOf
import core.basic.Subscriber
import ui.gui.processor.StateManager

class TokenInput(
    private val stateManager: StateManager,
    private val index: Int
) : DataInput(), Subscriber {
    override val state = mutableStateOf(stateManager.tokens[index])

    init {
        stateManager.subscribe(this)
    }

    override fun changed() {
        trigger.value = !trigger.value
        state.value = stateManager.inputs[index]
    }

    override fun execData(value: String) {
        stateManager.tokens[index] = value
    }
}
