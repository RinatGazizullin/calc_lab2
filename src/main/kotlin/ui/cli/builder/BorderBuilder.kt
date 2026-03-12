package ui.cli.builder

import core.exception.BuilderException
import core.model.Border
import core.utils.TextUtils
import ui.basic.CanBuild
import ui.basic.CanBuild.Companion.MAX_COUNT
import ui.cli.processor.InterfaceProcessor
import java.math.BigDecimal

class BorderBuilder(
    private val interfaceProcessor: InterfaceProcessor
) : CanBuild<Border, Set<String>> {
    companion object {
        private const val EPSILON = "Введите погрешность - "
        private const val LEFT = "Введите левую границу для <%s> - "
        private const val INTRO_MESSAGE = "Введем границы переменных"
        private const val RIGHT = "Введите правую границу для <%s> - "
        private const val NUMBER_ERROR = "Значение <%s> должно быть числом"
        private const val MORE_ERROR = "Левая граница должна быть меньше правой"
        private const val BUILDER_ERROR = "Не удалось получить границы измерения"
    }

    override fun build(data: Set<String>): Border {
        interfaceProcessor.renderMessage(INTRO_MESSAGE)
        val result: MutableMap<String, Pair<BigDecimal, BigDecimal>> = mutableMapOf()

        for (token in data) {
            result[token] = buildToken(token)
        }

        var epsilon: BigDecimal
        for (i in 1..MAX_COUNT) {
            val s = TextUtils.prepare(interfaceProcessor.readLine(EPSILON))

            try {
                epsilon = s.toBigDecimal()
            } catch (e: NumberFormatException) {
                interfaceProcessor.renderError(String.format(NUMBER_ERROR, s))
                continue
            }

            return Border(result, epsilon)
        }

        throw BuilderException(BUILDER_ERROR)
    }

    private fun buildToken(token: String): Pair<BigDecimal, BigDecimal> {
        for (i in 1..MAX_COUNT) {
            var left = BigDecimal.ONE
            var right = BigDecimal.ONE

            var flag = false
            for (j in 1..MAX_COUNT) {
                val s1 = TextUtils.prepare(interfaceProcessor.readLine(String.format(LEFT, token)))

                try {
                    left = s1.toBigDecimal()
                    flag = true
                    break
                } catch (e: NumberFormatException) {
                    interfaceProcessor.renderError(String.format(NUMBER_ERROR, s1))
                    continue
                }
            }

            if (!flag) {
                throw BuilderException(BUILDER_ERROR)
            }

            flag = false
            for (j in 1..MAX_COUNT) {
                val s2 = TextUtils.prepare(interfaceProcessor.readLine(String.format(RIGHT, token)))
                try {
                    right = s2.toBigDecimal()
                    flag = true
                    break
                } catch (e: NumberFormatException) {
                    interfaceProcessor.renderError(String.format(NUMBER_ERROR, s2))
                    continue
                }
            }

            if (!flag) {
                throw BuilderException(BUILDER_ERROR)
            }

            if (left >= right) {
                interfaceProcessor.renderError(MORE_ERROR)
                continue
            }

            return Pair(left, right)
        }
        throw BuilderException(BUILDER_ERROR)
    }
}
