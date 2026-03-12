package ui.gui.element.input

import androidx.compose.runtime.mutableStateOf
import core.basic.Subscriber
import ui.gui.processor.StateManager

class TokenInput(
    private val stateManager: StateManager,
    private val index: Int
) : DataInput(), Subscriber {
    override val state = mutableStateOf(stateManager.tokens[index])
    private var token = stateManager.tokens[index]

    init {
        stateManager.subscribe(this)
    }

    override fun changed() {
        state.value = stateManager.tokens[index]
        trigger.value = !trigger.value
    }

    override fun execData(value: String) {
        if (value.isEmpty()) {
            return
        }

        println(stateManager.borders)
        println(stateManager.tokens)

        if (value != token) {
            stateManager.borders[value] = stateManager.borders[token]!!
            stateManager.borders.remove(token)
        }
        token = value
        stateManager.tokens[index] = value
    }
}
