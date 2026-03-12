package core.solver

import core.basic.SystemSolver
import core.exception.ExpressionException
import core.model.Border
import core.model.Expression
import core.model.Result
import core.processor.ExpressionProcessor
import core.solver.IterationSolver.Companion
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

class SimpleSystemSolver : SystemSolver {
    override val name: String = "Метод простой итерации"

    companion object {
        private const val MAX_ITERATIONS = 1E5
        private const val ITER_ERROR = "Достигнуто максимальное количество итераций"
        private const val ENOUGH_ERROR = "Достаточное условие метода простой итерации не выполнено"
    }

    override fun solve(
        expressionProcessor: ExpressionProcessor,
        border: Border,
        tokens: Set<String>
    ): Result {
        var init = getInitialApproximation(border, tokens, expressionProcessor.size)
        val lambdas = mutableListOf<BigDecimal>()
        val tokensList = tokens.sorted()

        for (i in expressionProcessor.exps.indices) {
            val token = tokensList[i]
            val derivativeValue = expressionProcessor.derivativeByIndex(init, i, token)
            val lambda = -BigDecimal.ONE.divide(derivativeValue, MathContext(40, RoundingMode.HALF_UP))
            lambdas.add(lambda)
        }
        val phi = IntRange(0, expressionProcessor.size - 1).map { index ->
            Expression(
                expressionProcessor.size,
                expressionProcessor.tokens,
                { params: Map<String, BigDecimal> ->
                    params[tokensList[index]]!! +
                            lambdas[index] * expressionProcessor.calculateByIndex(params, index)
                }
            )
        }.toList()

        var iterations = 0

        do {
            check(phi, tokens, init)

            val newValue = init
            init = mutableMapOf()
            IntRange(0, expressionProcessor.size - 1).forEach {
                index -> init[tokensList[index]] = phi[index].calculate(init)
            }

            val maxDiff = newValue.keys.maxOf { key ->
                (newValue[key]!! - init[key]!!).abs()
            }

            if (++iterations > MAX_ITERATIONS) {
                throw ExpressionException(ITER_ERROR)
            }
        } while (maxDiff > border.epsilon)

        return core.model.Result(init)
    }

    private fun check(
        phi: List<Expression>,
        tokens: Set<String>,
        values: Map<String, BigDecimal>
    ) {
        val sorted = tokens.sorted()
        if (phi.maxOf { elem -> sorted.sumOf { token ->
            elem.derivative(values, token) } } >= BigDecimal.ONE) {
            throw ExpressionException(ENOUGH_ERROR)
        }
    }

    private fun getInitialApproximation(
        border: Border,
        tokens: Set<String>,
        size: Int
    ): MutableMap<String, BigDecimal> {
        val result = mutableMapOf<String, BigDecimal>()
        val sortedTokens = tokens.sorted()

        for (i in 0 until size) {
            val token = sortedTokens[i]
            val tokenBorder = border.borders[token]!!
            val initialValue =
                tokenBorder.first.add(tokenBorder.second)
                    .divide(BigDecimal.valueOf(2), MathContext(40, RoundingMode.HALF_UP))
            result[token] = initialValue
        }
        return result
    }
}
