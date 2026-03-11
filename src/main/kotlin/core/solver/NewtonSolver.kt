package core.solver

import core.basic.SingleSolver
import core.model.Expression
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class NewtonSolver : SingleSolver {
    override val name: String = "Метод касательных"

    companion object {
        private fun calculateNew(expression: Expression, value: BigDecimal, token: String): BigDecimal {
            return value - expression.calculate(value, token).divide(
                expression
                    .derivative(value, token), MathContext(40, RoundingMode.HALF_UP)
            )
        }
    }

    override fun solve(
        expression: Expression,
        left: BigDecimal,
        right: BigDecimal,
        epsilon: BigDecimal,
        token: String
    ): BigDecimal {
        verify(expression, left, right, token)
        var x =
            if (expression.calculate(left, token).signum()
                * expression.secondDerivative(left, token).signum() > 0
            )
                left
            else
                right

        do {
            x = calculateNew(expression, x, token)
        } while ((expression.calculate(x, token).divide(
                expression.derivative(x, token),
                MathContext(40, RoundingMode.HALF_UP)
            )).abs() > epsilon
        )

        return x
    }
}
