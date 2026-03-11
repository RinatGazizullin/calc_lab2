package ui.cli.command

import core.processor.ExpressionProcessor
import ui.cli.basic.CanRender
import ui.cli.basic.Command
import ui.cli.basic.HaveManual
import ui.cli.render.SystemRender

class Show(
    private val expressionProcessor: ExpressionProcessor,
    private val systemRender: CanRender<ExpressionProcessor>,
) : Command(Type.SHOW) {
    override val manual: String

    init {
        val builder = StringBuilder()
        builder.append(HaveManual.ManualBuilder.name(type.value))
        builder.append(HaveManual.ManualBuilder.description(type.description))
        builder.append(HaveManual.ManualBuilder.noArguments())
        manual = builder.toString()
    }

    override fun execute(arguments: Arguments): Result {
        return Result(systemRender.render(expressionProcessor) , Result.Code.GOOD)
    }
}
