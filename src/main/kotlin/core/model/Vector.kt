package core.model

import java.math.BigDecimal

class Vector(var results: Array<BigDecimal>) {
    val size = this.results.size

    companion object {
        fun empty(size: Int): Vector {
            return Vector(Array(size) { BigDecimal.ZERO })
        }
    }
}
