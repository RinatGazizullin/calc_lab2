package ui.gui.element.input

import androidx.compose.runtime.mutableStateOf
import core.basic.Subscriber
import ui.gui.processor.StateManager

class BorderInput(
    private val stateManager: StateManager,
    private val index: Int,
    private val left: Boolean
) : DataInput(), Subscriber {
    private var token = stateManager.tokens[index]
    override var state =
        mutableStateOf(
            if (left)
                stateManager.borders[token]!!.first
            else
                stateManager.borders[token]!!.second
        )

    init {
        stateManager.subscribe(this)
    }

    override fun changed() {
        if (token != stateManager.tokens[index]) {
            token = if (index == 0) {
                stateManager.first()
            } else {
                stateManager.second()
            }
        }
        trigger.value = !trigger.value
    }

    override fun execData(value: String) {
        if (left) {
            stateManager.borders[token] = Pair(value, stateManager.borders[token]!!.second)
        } else {
            stateManager.borders[token] = Pair(stateManager.borders[token]!!.first, value)
        }
    }
}
