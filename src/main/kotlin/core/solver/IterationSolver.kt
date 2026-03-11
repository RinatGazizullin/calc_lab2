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
            private const val MAX_ITERATIONS = 1E5
            private val CHECK_DERIVATIVE = BigDecimal("1E-3")
            private const val ENOUGH_ERROR = "Достаточное условие не выполнено"
            private const val ITER_ERROR = "Достигнуто максимальное количество итераций"
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
            val lambda = if (expression.derivative(left, token) > BigDecimal.ZERO)
                -BigDecimal.ONE.divide(maxDerivative, MathContext.DECIMAL128)
            else BigDecimal.ONE.divide(maxDerivative, MathContext.DECIMAL128)

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

            var x = (left + right).divide(BigDecimal.valueOf(2), MathContext.DECIMAL128)
            var new = phi.calculate(x, token)
            var iterations = 0

            do {
                x = new
                new = phi.calculate(x, token)
                iterations++

                if (iterations > MAX_ITERATIONS) {
                    throw ExpressionException(ITER_ERROR)
                }
            } while ((x - new).abs() > epsilon)

            return new
        }
    }
