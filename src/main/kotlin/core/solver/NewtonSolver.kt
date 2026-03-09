package core.solver

import core.basic.SingleSolver
import core.model.Equation
import java.math.BigDecimal

class NewtonSolver : SingleSolver {
    override val name: String = "Метод касательных"

    companion object {
        private fun calculateNew(equation: Equation, x: BigDecimal): BigDecimal {
            return x - equation.calculate(x) / equation.derivative(x)
        }
    }

    override fun solve(
        equation: Equation,
        left: BigDecimal,
        right: BigDecimal,
        epsilon: BigDecimal
    ): BigDecimal {
        var x = if (equation.calculate(left) * equation.secondDerivative(left) > BigDecimal.ZERO) left else right

        do {
            x = calculateNew(equation, x)
        } while ((equation.calculate(x) / equation.derivative(x)).abs() > epsilon)

        return x
    }
}
