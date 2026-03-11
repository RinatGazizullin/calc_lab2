package ui.command

import core.basic.SingleSolver
import core.basic.SystemSolver
import core.exception.BuilderException
import core.exception.ExpressionException
import core.exception.SolveException
import core.model.Border
import core.model.Result
import core.processor.ExpressionProcessor
import core.utils.TextUtils
import ui.basic.CanBuild
import ui.basic.CanRender
import ui.basic.Command
import ui.basic.HaveManual

class Solve(
    private val expressionProcessor: ExpressionProcessor,
    private val render: CanRender<core.model.Result>,
    private val builder: CanBuild<Border>,
    private val singles: List<SingleSolver>,
    private val systems: List<SystemSolver>
) : Command(Type.SOLVE) {
    override val manual: String

    init {
        val builder = StringBuilder()
        builder.append(HaveManual.ManualBuilder.name(type.value))
        builder.append(HaveManual.ManualBuilder.description(type.description))
        builder.append(HaveManual.ManualBuilder.some(ARGUMENT_NAME, ARGUMENT_DESCRIPTION))
        manual = builder.toString()
    }

    companion object {
        private const val ARGS_COUNT = 1
        private const val TOKENS_COUNT = 1
        private const val TOKENS_MULTI = 2
        private const val ARGUMENT_NAME = "<index>"
        private const val TOKENS_ERROR = "Неверное количество переменных"
        private const val ARGS_ERROR = "Значение <%s> должно быть числом"
        private const val ARGUMENT_DESCRIPTION = "Индекс НУ (нелинейного уравнения)"
        private const val NO_ARGS_ERROR = "Необходимо ввести один аргумент $ARGUMENT_NAME"
    }

    override fun execute(arguments: Arguments): Result {
        if (arguments.args.size >= ARGS_COUNT) {
            val index: Int

            try {
                index = TextUtils.prepare(arguments.args[0]).toInt() - 1
            } catch (e: NumberFormatException) {
                return Result(
                    String.format(ARGS_ERROR, arguments.args[0]), Result.Code.ERROR
                )
            }

            return executeSingle(index)
        } else {
            return executeMulti()
        }
    }

    private fun executeMulti(): Result {
        val tokens = expressionProcessor.tokens

        if (tokens.size != TOKENS_MULTI) {
            return Result(TOKENS_ERROR, Result.Code.ERROR)
        }

        val multi: Border
        try {
            multi = builder.build(tokens)
        } catch (e: BuilderException) {
            return Result(e.message!!, Result.Code.ERROR)
        }

        var counter = 1
        var first = true
        val result = StringBuilder()

        try {
            for (solver in systems) {
                if (first) first = false else result.appendLine()
                result.append("${counter++}) ${solver.name}").appendLine()
                try {
                    result.append(
                        solver.solve(
                            expressionProcessor,
                            multi,
                            tokens
                        )
                    )
                } catch (e: ExpressionException) {
                    result.append(e.message)
                }
            }
        }  catch (e: SolveException) {
            return Result(e.message!!, Result.Code.ERROR)
        }

        return Result(result.toString(), Result.Code.GOOD)
    }

    private fun executeSingle(index: Int): Result {
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

        val border: Border
        try {
            border = builder.build(tokens)
        } catch (e: BuilderException) {
            return Result(e.message!!, Result.Code.ERROR)
        }

        var counter = 1
        var first = true
        val token = tokens.first()
        val result = StringBuilder()

        try {
            for (solver in singles) {
                if (first) first = false else result.appendLine()
                result.append("${counter++}) ${solver.name}").appendLine()
                try {
                    result.append(
                        render.render(
                            solver.solve(
                                expression,
                                border,
                                token
                            )
                        )
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
