package core.basic

import core.exception.SolveException
import core.model.Border
import core.model.Expression
import core.model.Result
import ui.cli.builder.BorderBuilder
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

interface SingleSolver {
    val name: String

    companion object {
        private const val STEPS: Long = 100
        private const val ENOUGH_ERROR = "Достаточное условие не выполнено"
        private const val NECESSARY_ERROR = "Необходимое условие не выполнено"
        private const val BORDER_ERROR = "Отсутствуют данные граничных значений"
        private const val LEFT_ERROR = "Левая граница не может быть больше правой"
    }

    fun solve(
        expression: Expression,
        border: Border,
        token: String
    ): Result

    fun verify(
        expression: Expression,
        border: Border,
        token: String
    ) {
        val data = border.borders[token] ?: throw SolveException(BORDER_ERROR)
        val left = data.first
        val right = data.second

        if (left >= right) {
            throw SolveException(LEFT_ERROR)
        }

        if (expression.calculate(left, token).signum()
            * expression.calculate(right, token).signum() > 0) {
            throw SolveException(NECESSARY_ERROR)
        }

        val step = right.add(left.negate()).divide(
            BigDecimal.valueOf(STEPS),
            MathContext(40, RoundingMode.HALF_UP)
        )

        for (i in 0..<STEPS) {
            val pre = expression.derivative(
                left + step * BigDecimal
                    .valueOf(i), token
            ).signum() >= 0
            val now = expression.derivative(
                left + step * BigDecimal
                    .valueOf(i + 1), token
            ).signum() >= 0

            if (pre != now) {
                throw SolveException(ENOUGH_ERROR)
            }
        }
    }
}
