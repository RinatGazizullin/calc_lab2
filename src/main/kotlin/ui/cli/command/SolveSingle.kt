package ui.cli.command

import core.processor.ExpressionProcessor
import core.utils.TextUtils
import ui.cli.basic.Command
import ui.cli.basic.HaveManual
import java.math.BigDecimal

class SolveSingle(private val expressionProcessor: ExpressionProcessor) : Command(Type.SOLVE_SINGLE) {
    override val manual: String

    init {
        val builder = StringBuilder()
        builder.append(HaveManual.ManualBuilder.name(type.name))
        builder.append(HaveManual.ManualBuilder.description(type.description))
        builder.append(HaveManual.ManualBuilder.arguments(ARGUMENT_NAME, ARGUMENT_DESCRIPTION))
        manual = builder.toString()
    }

    companion object {
        private const val ARGS_COUNT = 3
        private const val ARGS_ERROR = "Аргументы должны быть числами"
        private const val ARGUMENT_NAME = "<left>, <right>, <accuracy>"
        private const val ARGUMENT_DESCRIPTION = "границы поиска решения, необходимая точность"
        private const val NO_ARGS_ERROR = "Необходимо ввести три аргумента <left>, <right>, <accuracy>"
    }

    override fun execute(arguments: Arguments): Result {
        val left: BigDecimal
        val right: BigDecimal
        val epsilon: BigDecimal

        if (arguments.args.size == ARGS_COUNT) {
            try {
                left = TextUtils.prepare(arguments.args[0]).toBigDecimal()
                right = TextUtils.prepare(arguments.args[1]).toBigDecimal()
                epsilon = TextUtils.prepare(arguments.args[2]).toBigDecimal()
            } catch (e: NumberFormatException) {
                return Result(ARGS_ERROR, Result.Code.ERROR)
            }
        } else {
            return Result(NO_ARGS_ERROR, Result.Code.ERROR)
        }

        TODO("In progress")
    }
}
