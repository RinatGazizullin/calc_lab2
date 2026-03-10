package core.processor

import core.exception.ExpressionException
import core.model.Expression
import core.model.Vector
import core.utils.Examples
import java.math.BigDecimal

class ExpressionProcessor {
    private val tokens: MutableSet<String> = mutableSetOf()
    val exps: MutableList<Expression> = mutableListOf(Examples.quickExample())
    var size: Int = exps.size

    init {
        checkTokens()
    }

    companion object {
        private const val MAX_SIZE = 20
        private const val SIZE_ERROR = "Размер выходит за границы размерности"
        private const val INDEX_ERROR = "Индекс выходит за границы размерности"
        private const val TOKEN_ERROR = "Количество переменных не соответствует размерности"
    }

    fun addExpression(newExpression: Expression) {
        tokens.addAll(newExpression.tokens)
        exps.add(newExpression)

        checkSize()
    }

    fun checkIndex(index: Int) {
        if (index >= size || index < 0) {
            throw ExpressionException(INDEX_ERROR)
        }
    }

    fun changeExpression(newExpression: Expression, index: Int) {
        checkIndex(index)
        exps[index] = newExpression
        checkSize()
        reCalcTokens()
    }

    fun changeSize(newSize: Int) {
        if (newSize > MAX_SIZE || newSize <= 0) {
            throw ExpressionException(SIZE_ERROR)
        }

        if (newSize < size) {
            repeat(size - newSize) {
                exps.removeAt(exps.lastIndex)
            }
        } else if (newSize > size) {
            repeat(newSize - size) {
                exps.add(Examples.quickExample())
            }
        }

        checkSize()
        reCalcTokens()
    }

    private fun checkSize() {
        size = exps.size
    }

    private fun reCalcTokens() {
        tokens.clear()
        tokens.addAll(exps.flatMap { elem -> elem.tokens }.toSet())
    }

    private fun checkTokens() {
        if (tokens.size > size) {
            throw ExpressionException(TOKEN_ERROR)
        }
    }

    fun calculate(params: Map<String, BigDecimal>): Vector {
        checkTokens()
        val result = Vector.empty(size)
        IntRange(0, size).forEach { index -> result.results[index] = exps[index].calculate(params) }
        return result
    }

    fun calculateByIndex(params: Map<String, BigDecimal>, index: Int): BigDecimal {
        checkIndex(index)
        return exps[index].calculate(params)
    }

    fun derivative(params: Map<String, BigDecimal>): Vector {
        checkTokens()
        val result = Vector.empty(size)
        IntRange(0, size).forEach { index -> result.results[index] = exps[index].derivative(params) }
        return result
    }

    fun derivativeByIndex(params: Map<String, BigDecimal>, index: Int): BigDecimal {
        checkIndex(index)
        return exps[index].derivative(params)
    }

    fun secondDerivative(param: Map<String, BigDecimal>): Vector {
        checkTokens()
        val result = Vector.empty(size)
        IntRange(0, size).forEach { index -> result.results[index] = exps[index].secondDerivative(param) }
        return result
    }

    fun secondDerivativeByIndex(params: Map<String, BigDecimal>, index: Int): BigDecimal {
        checkIndex(index)
        return exps[index].secondDerivative(params)
    }
}
