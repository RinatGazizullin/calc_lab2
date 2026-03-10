package ui.cli.command

import core.processor.ExpressionProcessor
import ui.cli.basic.Command
import ui.cli.basic.HaveManual

class Size(private val expressionProcessor: ExpressionProcessor) : Command(Type.SIZE) {
    override val manual: String

    init {
        val builder = StringBuilder()
        builder.append(HaveManual.ManualBuilder.name(type.name))
        builder.append(HaveManual.ManualBuilder.description(type.description))
        builder.append(HaveManual.ManualBuilder.arguments(ARGUMENT_NAME, ARGUMENT_DESCRIPTION))
        manual = builder.toString()
    }

    companion object {
        private const val ARGUMENT_NAME = "<size>"
        private const val ARGUMENT_DESCRIPTION = "новый размер СНУ (системы нелинейных уравнений)"
        private const val MIN_ARGS = 1
        private const val GOOD_MESSAGE = "Размер СНУ успешно изменен"
        private const val NO_ARGS_MESSAGE = "Не был введен аргумент $ARGUMENT_NAME"
        private const val FORMAT_MESSAGE = "Значение $ARGUMENT_NAME должно быть числом"
        private const val MIN_SIZE = 1
        private const val MAX_SIZE = 20
        private const val SIZE_MESSAGE = "Размер матрицы должен быть от $MIN_SIZE до $MAX_SIZE"
    }

    override fun execute(arguments: Arguments): Result {
        if (arguments.args.size < MIN_ARGS) {
            return Result(NO_ARGS_MESSAGE, Result.Code.ERROR)
        }
        try {
            val newSize = arguments.args[0].toInt();
            if (newSize < MIN_SIZE || newSize > MAX_SIZE) {
                return Result(SIZE_MESSAGE, Result.Code.ERROR)
            }

            TODO("In progress")
        } catch (e: NumberFormatException) {
            return Result(FORMAT_MESSAGE, Result.Code.ERROR)
        }
    }
}
