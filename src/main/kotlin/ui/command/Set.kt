package ui.cli.command

import core.exception.BuilderException
import core.exception.ExpressionException
import core.model.Expression
import core.processor.ExpressionProcessor
import ui.cli.basic.CanBuild
import ui.cli.basic.Command
import ui.cli.basic.HaveManual

class Set(
    private val expressionProcessor: ExpressionProcessor,
    private val builder: CanBuild<Expression>
) : Command(Type.SET) {
    override val manual: String

    companion object {
        private const val MIN_ARGS = 1
        private const val ARGUMENT_NAME = "<index>"
        private const val NO_ARGS_MESSAGE = "Не был введен аргумент $ARGUMENT_NAME"
        private const val GOOD_MESSAGE = "СНУ (Система нелинейных уравнений) изменена!"
        private const val FORMAT_MESSAGE = "Значение $ARGUMENT_NAME должно быть числом"
        private const val ARGUMENT_DESCRIPTION = "Индекс НУ (нелинейного уравнения)"
    }

    init {
        val builder = StringBuilder()
        builder.append(HaveManual.ManualBuilder.name(type.value))
        builder.append(HaveManual.ManualBuilder.description(type.description))
        builder.append(HaveManual.ManualBuilder.arguments(ARGUMENT_NAME, ARGUMENT_DESCRIPTION))
        manual = builder.toString()
    }

    override fun execute(arguments: Arguments): Result {
        if (arguments.args.size != MIN_ARGS) {
            return Result(NO_ARGS_MESSAGE, Result.Code.ERROR)
        } else {
            try {
                val index = arguments.args[0].toInt() - 1
                expressionProcessor.checkIndex(index)
                expressionProcessor.changeExpression(builder
                    .build(expressionProcessor.tokens), index)
                return Result(GOOD_MESSAGE, Result.Code.GOOD)
            } catch (e: NumberFormatException) {
                return Result(FORMAT_MESSAGE, Result.Code.ERROR)
            } catch (e: ExpressionException) {
                return Result(e.message!!, Result.Code.ERROR)
            } catch (e: BuilderException) {
                return Result(e.message!!, Result.Code.ERROR)
            }
        }
    }
}
