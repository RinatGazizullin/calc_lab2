package core.basic

import core.model.Border
import core.model.Result
import core.processor.ExpressionProcessor

interface SystemSolver {
    val name: String

    fun solve(
        expressionProcessor: ExpressionProcessor,
        border: Border,
        tokens: Set<String>
    ): Result
}
