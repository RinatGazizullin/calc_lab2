package core.solver

import core.basic.SingleSolver
import core.exception.ExpressionException
import core.model.Expression
import java.math.BigDecimal
import java.math.MathContext

class IterationSolver : SingleSolver {
    override val name: String = "Метод простой итерации"

    companion object {
        private const val STEPS: Long = 100
        private val CHECK_DERIVATIVE = BigDecimal("1E-3")
        private const val ENOUGH_ERROR = "Достаточное условие не выполнено"
    }

    override fun solve(
        expression: Expression,
        left: BigDecimal,
        right: BigDecimal,
        epsilon: BigDecimal,
        token: String
    ): BigDecimal {
        verify(expression, left, right, token)

        val step = right.add(left.negate()).divide(BigDecimal.valueOf(STEPS), MathContext.DECIMAL128)
        val maxDerivative = LongRange(0, STEPS).maxOf { index ->
            expression.derivative(left + step * BigDecimal.valueOf(index), token).abs() }
        val lambda = if (expression.calculate(left, token) > BigDecimal.ZERO)
            -BigDecimal.ONE / maxDerivative else BigDecimal.ONE / maxDerivative

        val phi = Expression(1, setOf(token)) { data: Map<String, BigDecimal> ->
            data[token]!! + lambda * expression.calculate(
                data[token]!!, token
            )
        }

        val q = LongRange(0, STEPS).maxOf { index ->
            phi.derivative(left + step * BigDecimal.valueOf(index), token).abs() }

        if (q - BigDecimal.ONE >= CHECK_DERIVATIVE) {
            throw ExpressionException(ENOUGH_ERROR)
        }

        var x = (left + right) / BigDecimal.valueOf(2)
        var new = phi.calculate(x, token)

        do {
            x = new
            new = phi.calculate(x, token)
        } while ((x - new).abs() > epsilon)

        return new
    }
}
