package ui.cli.command

import ui.cli.basic.Command
import ui.cli.basic.HaveManual

class Exit : Command(Type.EXIT) {
    override val manual: String

    init {
        val builder = StringBuilder()
        builder.append(HaveManual.ManualBuilder.name(type.value))
        builder.append(HaveManual.ManualBuilder.description(type.description))
        builder.append(HaveManual.ManualBuilder.noArguments())
        manual = builder.toString()
    }

    companion object {
        private const val EXIT_MESSAGE = "Завершение работы!"
    }

    override fun execute(arguments: Arguments): Result {
        return Result(EXIT_MESSAGE, Result.Code.EXIT)
    }
}
