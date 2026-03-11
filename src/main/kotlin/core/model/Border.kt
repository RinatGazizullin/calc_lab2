package core.model

import java.math.BigDecimal

class Border(
    val borders: Map<String, Pair<BigDecimal, BigDecimal>>,
    val epsilon: BigDecimal
)