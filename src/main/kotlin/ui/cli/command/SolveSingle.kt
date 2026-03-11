package ui.cli.command

import core.basic.SingleSolver
import core.exception.BuilderException
import core.exception.ExpressionException
import core.exception.SolveException
import core.processor.ExpressionProcessor
import core.utils.TextUtils
import ui.cli.basic.CanBuild
import ui.cli.basic.Command
import ui.cli.basic.HaveManual
import ui.cli.builder.BorderBuilder

class SolveSingle(
    private val expressionProcessor: ExpressionProcessor,
    private val builder: CanBuild<BorderBuilder.Border>,
    private val solvers: List<SingleSolver>
) : Command(Type.SOLVE_SINGLE) {
    override val manual: String

    init {
        val builder = StringBuilder()
        builder.append(HaveManual.ManualBuilder.name(type.value))
        builder.append(HaveManual.ManualBuilder.description(type.description))
        builder.append(HaveManual.ManualBuilder.arguments(ARGUMENT_NAME, ARGUMENT_DESCRIPTION))
        manual = builder.toString()
    }

    companion object {
        private const val ARGS_COUNT = 1
        private const val TOKENS_COUNT = 1
        private const val ARGUMENT_NAME = "<index>"
        private const val TOKENS_ERROR = "Неверное количество переменных"
        private const val ARGS_ERROR = "Значение <%s> должно быть числом"
        private const val ARGUMENT_DESCRIPTION = "Индекс НУ (нелинейного уравнения)"
        private const val NO_ARGS_ERROR = "Необходимо ввести один аргумент <index>"
    }

    override fun execute(arguments: Arguments): Result {
        val index: Int

        if (arguments.args.size == ARGS_COUNT) {
            try {
                index = TextUtils.prepare(arguments.args[0]).toInt() - 1
            } catch (e: NumberFormatException) {
                return Result(
                    String.format(ARGS_ERROR, arguments.args[0]), Result.Code.ERROR
                )
            }
        } else {
            return Result(NO_ARGS_ERROR, Result.Code.ERROR)
        }

        try {
            expressionProcessor.checkIndex(index)
        } catch (e: ExpressionException) {
            return Result(e.message!!, Result.Code.ERROR)
        }

        val expression = expressionProcessor.exps[index]
        val tokens = expression.tokens
        if (tokens.size != TOKENS_COUNT) {
            return Result(TOKENS_ERROR, Result.Code.ERROR)
        }

        val border: BorderBuilder.Border
        try {
            border = builder.build(tokens)
        } catch (e: BuilderException) {
            return Result(e.message!!, Result.Code.ERROR)
        }
        val result = StringBuilder()
        val token = tokens.first()
        var first = true
        var counter = 1

        try {
            for (solver in solvers) {
                if (first) first = false else result.appendLine()
                result.append("${counter++}) ${solver.name}").appendLine()
                try {
                    result.append(
                        "$token = ${
                            solver.solve(
                                expression,
                                border.borders[token]!!.first,
                                border.borders[token]!!.second,
                                border.epsilon,
                                token
                            )
                        }"
                    )
                } catch (e: ExpressionException) {
                    result.append(e.message)
                }
            }
        } catch (e: SolveException) {
            return Result(e.message!!, Result.Code.ERROR)
        }
        return Result(result.toString(), Result.Code.GOOD)
    }
}
