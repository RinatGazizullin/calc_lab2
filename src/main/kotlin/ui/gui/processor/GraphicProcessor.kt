package ui.gui.processor

import ui.basic.Command
import ui.basic.Processor

class GraphicProcessor : Processor {
    private val dataProcessor = DataProcessor()

    override fun start() {
        ApplicationProcessor(
            dataProcessor.expressionProcessor,
            this,
            dataProcessor.stateManager
        ).showWindow()
    }

    fun prepare() {
        executeCommand(Command.Type.SET, Command.Arguments(listOf("1")))
        executeCommand(Command.Type.SET, Command.Arguments(listOf("2")))
    }

    private fun showText(result: Command.Result) {
        dataProcessor.stateManager.outputData = result.message
    }

    fun executeCommand(
        type: Command.Type,
        args: Command.Arguments = Command.Arguments(listOf())
    ) {
        if (dataProcessor.commands.containsKey(type)) {
            showText(dataProcessor.commands[type]!!.execute(args))
        } else {
            showText(
                Command.Result(
                    "Команда <${type.value}> не была найдена",
                    Command.Result.Code.ERROR
                )
            )
        }
    }

    fun endCommands() {
        dataProcessor.stateManager.sendMessage()
    }
}
