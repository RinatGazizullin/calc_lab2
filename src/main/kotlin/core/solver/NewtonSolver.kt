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
        val a = left
        val b = right
        var x = if (equation.calculate(a) * equation.secondDerivative(a) > BigDecimal.ZERO) a else b

        do {
            x = calculateNew(equation, x)
        } while ((equation.calculate(x) / equation.derivative(x)).abs() > epsilon)

        return x
    }
}
