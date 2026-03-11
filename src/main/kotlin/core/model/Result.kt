package core.model

import java.math.BigDecimal

class Result(var results: MutableMap<String, BigDecimal>) {
    val size = this.results.size

    companion object {
        fun empty(params: Set<String>): Result {
            return Result(params.associateWith { BigDecimal.ZERO }.toMutableMap())
        }
    }
}
