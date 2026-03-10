package core.processor

import core.basic.SingleSolver
import core.exception.SolveException
import java.math.BigDecimal

class SolverProcessor(private val expressionProcessor: ExpressionProcessor) {
    companion object {
        private const val TOKEN_ERROR = "Неверное количество переменных"
        private const val INDEX_ERROR = "Индекс выходит за границы размерности"
    }

    fun solveSingle(
        left: BigDecimal,
        right: BigDecimal,
        epsilon: BigDecimal,
        single: SingleSolver,
        index: Int = 0
    ): String {
        if (index < 0 || index >= expressionProcessor.size) {
            throw SolveException(INDEX_ERROR)
        }

        val tokens = expressionProcessor.exps[index].tokens
        if (tokens.size != 1) {
            throw SolveException(TOKEN_ERROR)
        } else {
            return "${tokens.first()} = ${single.solve(expressionProcessor.exps[index],
                left, right, epsilon, tokens.first())}"
        }
    }
}
