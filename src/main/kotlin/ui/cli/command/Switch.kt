package ui.cli.command

import core.exception.EnumException
import ui.cli.basic.Command
import ui.cli.basic.HaveManual
import ui.cli.basic.Manager
import ui.cli.processor.DataProcessor

class Switch(private val dataProcessor: DataProcessor) : Command(Type.SWITCH) {
    override val manual: String

    init {
        val builder = StringBuilder()
        builder.append(HaveManual.ManualBuilder.name(type.name))
        builder.append(HaveManual.ManualBuilder.description(type.description))
        builder.append(HaveManual.ManualBuilder.someArguments(ARGUMENT_NAME, ARGUMENT_DESCRIPTION))
        manual = builder.toString()
    }

    companion object {
        private const val ARGS_COUNT = 1
        private const val ARGUMENT_NAME = "<mode>"
        private const val ARGUMENT_DESCRIPTION = "режим работы программы"
    }

    override fun execute(arguments: Arguments): Result {
        return if (arguments.args.size != ARGS_COUNT) {
            Result(dataProcessor.switchMode(), Result.Code.GOOD)
        } else {
            try {
                Result(dataProcessor.switchMode(Manager.Mode.fromString(arguments.args[0])), Result.Code.GOOD)
            } catch (e: EnumException) {
                Result(e.message!!, Result.Code.ERROR)
            }
        }
    }
}
