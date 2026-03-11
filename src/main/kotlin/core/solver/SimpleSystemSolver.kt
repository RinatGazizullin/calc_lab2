package core.solver

import core.basic.SystemSolver
import core.model.Border
import core.model.Result
import core.processor.ExpressionProcessor

class SimpleSystemSolver : SystemSolver {
    override val name: String = "Метод простой итерации"

    override fun solve(
        expressionProcessor: ExpressionProcessor,
        border: Border,
        tokens: Set<String>
    ): Result {
        TODO("Not yet implemented")
    }
}
