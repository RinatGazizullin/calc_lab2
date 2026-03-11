package ui.cli.processor

import ui.basic.Command

class CommandLineProcessor(private val interfaceProcessor: InterfaceProcessor) {
    companion object {
        private const val START = "Для вывода команд введите help!"
        private const val NO_COMMAND_ERROR = "Команда <%s> не была найдена"
        private const val MODE_ERROR = "Команда <%s> недоступна в данном режиме"
    }

    private val dataProcessor = DataProcessor(interfaceProcessor)

    fun start() {
        interfaceProcessor.renderMessage(START)
        while (true) {
            val line = interfaceProcessor.readLine().trim()
            if (line.isNotEmpty()) {
                val data = line.split(" ")
                val name = data[0]
                val args = Command.Arguments(data.drop(1))

                if (Command.Type.canString(name)) {
                    val command = Command.Type.fromString(name)
                    if ((dataProcessor.commands.containsKey(command))) {
                        val result = dataProcessor.commands[command]!!.execute(args)

                        when (result.result) {
                            Command.Result.Code.GOOD -> {
                                interfaceProcessor.renderMessage(result.message)
                            }
                            Command.Result.Code.ERROR -> {
                                interfaceProcessor.renderError(result.message)
                            }
                            Command.Result.Code.EXIT -> {
                                interfaceProcessor.renderMessage(result.message)
                                break
                            }
                        }
                    } else {
                        interfaceProcessor.renderError(String.format(MODE_ERROR, name))
                    }
                } else {
                    interfaceProcessor.renderError(String.format(NO_COMMAND_ERROR, name))
                }
            }
        }
    }
}
