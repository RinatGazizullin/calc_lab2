package core.solver

import core.basic.SingleSolver
import core.model.Expression
import java.math.BigDecimal

class NewtonSolver : SingleSolver {
    override val name: String = "Метод касательных"

    companion object {
        private fun calculateNew(expression: Expression, value: BigDecimal, token: String): BigDecimal {
            return value - expression.calculate(value, token) / expression.derivative(value, token)
        }
    }

    override fun solve(
        expression: Expression,
        left: BigDecimal,
        right: BigDecimal,
        epsilon: BigDecimal,
        token: String
    ): BigDecimal {
        var x =
            if (expression.calculate(left, token) * expression.secondDerivative(left, token) > BigDecimal.ZERO)
                left
            else
                right

        do {
            x = calculateNew(expression, x, token)
        } while ((expression.calculate(x, token) / expression.derivative(x, token)).abs() > epsilon)

        return x
    }
}
