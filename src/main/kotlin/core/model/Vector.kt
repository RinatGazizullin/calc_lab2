package core.model

import java.math.BigDecimal

class Vector(var vector: Array<BigDecimal>) {
    val size = this.vector.size

    companion object {
        fun empty(size: Int): Vector {
            return Vector(Array(size) { BigDecimal.ZERO })
        }
    }
}
