package ui.gui.processor

import ui.basic.Command
import ui.basic.Processor

class GraphicProcessor : Processor {
    val dataProcessor = DataProcessor()

    companion object {
        private const val NO_COMMAND_ERROR = "Команда <%s> не была найдена"
    }

    override fun start() {
        ApplicationProcessor(dataProcessor.expressionProcessor, this).showWindow()
    }

    fun executeCommand(
        type: Command.Type,
        args: Command.Arguments = Command.Arguments(listOf())
    ): Command.Result {
        if (dataProcessor.commands.containsKey(type)) {
            return dataProcessor.commands[type]!!.execute(args)
        }
        return Command.Result(
            String.format(NO_COMMAND_ERROR, type.value),
            Command.Result.Code.ERROR
        )
    }
}
