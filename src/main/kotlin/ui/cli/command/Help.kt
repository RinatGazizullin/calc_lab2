package ui.cli.command

import ui.cli.basic.Command
import ui.cli.basic.HaveManual

class Help : Command(Type.HELP) {
    override val manual: String

    init {
        val builder = StringBuilder()
        builder.append(HaveManual.ManualBuilder.name(type.name))
        builder.append(HaveManual.ManualBuilder.description(type.description))
        builder.append(HaveManual.ManualBuilder.noArguments())
        manual = builder.toString()
    }

    override fun execute(arguments: Arguments): Result {
        val message = Type.entries
            .mapIndexed { index, type -> "${index + 1}) ${type.value} - ${type.description}" }
            .joinToString("\n")
        return Result(message, Result.Code.GOOD)
    }
}
