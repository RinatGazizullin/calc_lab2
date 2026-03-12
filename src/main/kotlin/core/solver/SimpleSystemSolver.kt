package core.solver

import core.basic.SystemSolver
import core.exception.ExpressionException
import core.model.Border
import core.model.Expression
import core.model.Result
import core.processor.ExpressionProcessor
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

    private fun sorted(
        expressions: MutableList<Expression>,
        tokens: List<String>,
        values: Map<String, BigDecimal>
    ): MutableList<Expression> {
        val result = mutableListOf<Expression>()
        val exits = expressions.toMutableList()

        for (token in tokens) {
            result.add(exits.sortedBy { elem -> elem.derivative(values, token).abs() }.reversed().first())
            exits.remove(result.last())
        }

        return result
    }

    override fun solve(
        expressionProcessor: ExpressionProcessor,
        border: Border,
        tokens: Set<String>
    ): Result {
        val tokensList = tokens.sorted()
        val lambdas = mutableListOf<BigDecimal>()
        var init = getInitialApproximation(border, tokensList, expressionProcessor.size)
        val expressions = sorted(expressionProcessor.exps, tokensList, init)

        for (i in expressions.indices) {
            val token = tokensList[i]
            val derivativeValue = expressions[i].derivative(init, token)
            val lambda = -BigDecimal.ONE.divide(derivativeValue, MathContext(40, RoundingMode.HALF_UP))
            lambdas.add(lambda)
        }

        val phi = IntRange(0, expressionProcessor.size - 1).map { index ->
            Expression(
                expressionProcessor.size,
                tokens,
                { params: Map<String, BigDecimal> ->
                    params[tokensList[index]]!! +
                            lambdas[index] * expressions[index].calculate(params)
                }
            )
        }.toList()

        check(phi, tokensList, init)
        var iterations = 0

        do {
            val newValue = init
            init = mutableMapOf()
            IntRange(0, expressionProcessor.size - 1).forEach {
                index -> init[tokensList[index]] = phi[index].calculate(newValue)
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
        tokens: List<String>,
        values: Map<String, BigDecimal>
    ) {
        if (phi.maxOf { elem -> tokens.sumOf { token ->
            elem.derivative(values, token) } } >= BigDecimal(10)) {
            throw ExpressionException(ENOUGH_ERROR)
        }
    }

    private fun getInitialApproximation(
        border: Border,
        tokens: List<String>,
        size: Int
    ): MutableMap<String, BigDecimal> {
        val result = mutableMapOf<String, BigDecimal>()
        for (i in 0 until size) {
            val token = tokens[i]
            val tokenBorder = border.borders[token]!!
            val initialValue =
                tokenBorder.first.add(tokenBorder.second)
                    .divide(BigDecimal.valueOf(2), MathContext(40, RoundingMode.HALF_UP))
            result[token] = initialValue
        }
        return result
    }
}
