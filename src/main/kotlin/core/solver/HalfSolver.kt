package core.solver

import core.basic.SingleSolver
import core.model.Border
import core.model.Expression
import core.model.Result
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class HalfSolver : SingleSolver {
    override val name: String = "Метод половинного деления"

    companion object {
        private fun calculateMiddle(a: BigDecimal, b: BigDecimal): BigDecimal {
            return (a + b).divide(BigDecimal.valueOf(2), MathContext(40, RoundingMode.HALF_UP))
        }
    }

    override fun solve(
        expression: Expression,
        border: Border,
        token: String
    ): Result {
        verify(expression, border, token)
        var a = border.borders[token]!!.first
        var b = border.borders[token]!!.second
        var x = calculateMiddle(a, b)

        do {
            if (expression.calculate(x, token).signum()
                * expression.calculate(b, token).signum() < 0) {
                a = x
            } else {
                b = x
            }
            x = calculateMiddle(a, b)
        } while ((a - b).abs() > border.epsilon)

        return Result(mutableMapOf(token to x))
    }
}
