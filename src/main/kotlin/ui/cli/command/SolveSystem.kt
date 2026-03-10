package ui.cli.command

import core.processor.ExpressionProcessor
import ui.cli.basic.Command
import ui.cli.basic.HaveManual

class SolveSystem(private val expressionProcessor: ExpressionProcessor) : Command(Type.SOLVE_SYSTEM) {
    override val manual: String

    init {
        val builder = StringBuilder()
        builder.append(HaveManual.ManualBuilder.name(type.value))
        builder.append(HaveManual.ManualBuilder.description(type.description))
        builder.append(HaveManual.ManualBuilder.noArguments())
        manual = builder.toString()
    }

    override fun execute(arguments: Arguments): Result {
        TODO("Not yet implemented")
    }
}
