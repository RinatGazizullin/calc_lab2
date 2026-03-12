package ui.gui.element.input

import androidx.compose.runtime.mutableStateOf
import ui.gui.processor.StateManager

class ExpressionInput(
    // private val graphicProcessor: GraphicProcessor,
    private val stateManager: StateManager,
    private val index: Int
) : DataInput() {
    override var state = mutableStateOf(stateManager.inputs[index])

    init {
        stateManager.subscribe(this)
    }

    override fun changed() {
        trigger.value = !trigger.value
        state.value = stateManager.inputs[index]
    }

    /*
    override fun execFocus(value: String) {
        graphicProcessor.executeCommand(
            Command.Type.SET,
            Command.Arguments(listOf((index + 1).toString()))
        )
    }
     */

    override fun execData(value: String) {
        stateManager.inputs[index] = value
    }
}
