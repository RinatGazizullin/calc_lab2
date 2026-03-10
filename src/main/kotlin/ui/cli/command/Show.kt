package ui.cli.command

import ui.cli.basic.Command
import ui.cli.basic.HaveManual

class Show : Command(Type.SHOW) {
    override val manual: String

    init {
        val builder = StringBuilder()
        builder.append(HaveManual.ManualBuilder.name(type.name))
        builder.append(HaveManual.ManualBuilder.description(type.description))
        builder.append(HaveManual.ManualBuilder.noArguments())
        manual = builder.toString()
    }

    override fun execute(arguments: Arguments): Result {
        TODO("Not yet implemented")
    }
}
