package ui.gui.builder

import core.exception.BuilderException
import core.model.Border
import core.processor.ExpressionProcessor
import core.utils.TextUtils
import ui.basic.CanBuild
import ui.gui.processor.StateManager
import java.math.BigDecimal

class BorderGuiBuilder(
    private val expressionProcessor: ExpressionProcessor,
    private val stateManager: StateManager
) : CanBuild<Border, Set<String>> {
    override fun build(tokens: Set<String>): Border {
        val checkTokens = stateManager.tokens
        val borders = stateManager.borders

        for (token in tokens) {
            if (!checkTokens.contains(token) || !borders.containsKey(token)) {
                throw BuilderException("Не был введен нужный $token токен")
            }
        }

        val result: MutableMap<String, Pair<BigDecimal, BigDecimal>> = mutableMapOf()

        for (token in tokens) {
            val left: BigDecimal
            val right: BigDecimal

            val s1 = TextUtils.prepare(borders[token]!!.first)
            try {
                left = s1.toBigDecimal()
            } catch (e: NumberFormatException) {
                throw BuilderException("Значение $s1 должно быть числом")
            }

            val s2 = TextUtils.prepare(borders[token]!!.second)
            try {
                right = s2.toBigDecimal()
            } catch (e: NumberFormatException) {
                throw BuilderException("Значение $s2 должно быть числом")
            }

            if (left >= right) {
                throw BuilderException("Левая граница должна быть меньше правой")
            }

            result[token] = Pair(left, right)
        }

        val s = TextUtils.prepare(stateManager.epsilon)
        try {
            return Border(result, s.toBigDecimal())
        } catch (e: NumberFormatException) {
            throw BuilderException("Значение $s должно быть числом")
        }
    }
}
