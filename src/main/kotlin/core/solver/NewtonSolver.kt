package core.solver

import core.basic.SingleSolver
import core.exception.ExpressionException
import core.model.Border
import core.model.Expression
import core.model.Result
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class NewtonSolver : SingleSolver {
    override val name: String = "Метод касательных"

    companion object {
        private const val MAX_ITERATIONS = 1E5
        private const val ITER_ERROR = "Достигнуто максимальное количество итераций"

        private fun calculateNew(expression: Expression, value: BigDecimal, token: String): BigDecimal {
            return value - expression.calculate(value, token).divide(
                expression
                    .derivative(value, token), MathContext(40, RoundingMode.HALF_UP)
            )
        }
    }

    override fun solve(
        expression: Expression,
        border: Border,
        token: String
    ): Result {
        verify(expression, border, token)
        val left = border.borders[token]!!.first
        val right = border.borders[token]!!.second

        var x =
            if (expression.calculate(left, token).signum()
                * expression.secondDerivative(left, token).signum() > 0
            )
                left
            else
                right

        var iterations = 0
        do {
            x = calculateNew(expression, x, token)

            if (++iterations > MAX_ITERATIONS) {
                throw ExpressionException(ITER_ERROR)
            }
        } while ((expression.calculate(x, token).divide(
                expression.derivative(x, token),
                MathContext(40, RoundingMode.HALF_UP)
            )).abs() > border.epsilon
        )

        return Result(mutableMapOf(token to x))
    }
}
