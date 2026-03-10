package ui.cli.command

import core.processor.ExpressionProcessor
import ui.cli.basic.Command
import ui.cli.basic.HaveManual

class Show(private val expressionProcessor: ExpressionProcessor) : Command(Type.SHOW) {
    override val manual: String

    init {
        val builder = StringBuilder()
        builder.append(HaveManual.ManualBuilder.name(type.value))
        builder.append(HaveManual.ManualBuilder.description(type.description))
        builder.append(HaveManual.ManualBuilder.noArguments())
        manual = builder.toString()
    }

    override fun execute(arguments: Arguments): Result {
        var counter = 1
        return Result(IntRange(0, expressionProcessor.size - 1).joinToString("\n") { index ->
            "${counter++}) f(...) = ${expressionProcessor.exps[index].body}"
        }, Result.Code.GOOD)
    }
}
