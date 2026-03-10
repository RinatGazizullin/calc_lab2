package core.basic

import core.model.Vector


interface SystemSolver {
    val name: String

    fun solve(): Vector
}
