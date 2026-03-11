package ui.command

import core.processor.ExpressionProcessor
import ui.basic.Command
import ui.basic.HaveManual

class Build(private val expressionProcessor: ExpressionProcessor) : Command(Type.BUILD) {
    override val manual: String

    init {
        val builder = StringBuilder()
        builder.append(HaveManual.ManualBuilder.name(type.value))
        builder.append(HaveManual.ManualBuilder.description(type.description))
        builder.append(HaveManual.ManualBuilder.noArguments());
        manual = builder.toString()
    }

    override fun execute(arguments: Arguments): Result {
        TODO("Not yet implemented")
    }
}
