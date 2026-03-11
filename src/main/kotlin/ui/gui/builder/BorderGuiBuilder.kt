package ui.gui.builder

import core.exception.BuilderException
import core.model.Border
import core.utils.TextUtils
import ui.basic.CanBuild
import ui.basic.CanBuild.Companion.MAX_COUNT
import ui.cli.processor.InterfaceProcessor
import java.math.BigDecimal

class BorderGuiBuilder() : CanBuild<Border> {
    companion object {
        private const val EPSILON = "Введите погрешность - "
        private const val LEFT = "Введите левую границу для <%s> - "
        private const val INTRO_MESSAGE = "Введем границы переменных"
        private const val RIGHT = "Введите правую границу для <%s> - "
        private const val NUMBER_ERROR = "Значение <%s> должно быть числом"
        private const val MORE_ERROR = "Левая граница должна быть меньше правой"
        private const val BUILDER_ERROR = "Не удалось получить границы измерения"
    }

    override fun build(tokens: Set<String>): Border {
        TODO("Not et implemented")
    }
}
