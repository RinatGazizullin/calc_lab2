package core.basic

import core.exception.SolveException
import core.model.Expression
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

interface SingleSolver {
    val name: String

    companion object {
        private const val STEPS: Long = 100
        private const val ENOUGH_ERROR = "Достаточное условие не выполнено"
        private const val NECESSARY_ERROR = "Необходимое условие не выполнено"
        private const val LEFT_ERROR = "Левая граница не может быть больше правой"
    }

    fun solve(
        expression: Expression,
        left: BigDecimal,
        right: BigDecimal,
        epsilon: BigDecimal,
        token: String
    ): BigDecimal

    fun verify(
        expression: Expression,
        left: BigDecimal,
        right: BigDecimal,
        token: String
    ) {
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
