package ui.cli.basic

import core.exception.EnumException
import ui.cli.basic.Command.Type

interface Manager {
    val commands: Map<Command.Type, Command>

    enum class Mode(val description: String) {
        BOTH("Универсальный режим решения"),
        SYSTEM("Режим решения СНУ (системы нелинейных уравнений)"),
        SINGLE("Режим решения НУ (нелинейных уравнений)");

        companion object {
            private const val NO_ENUM_ERROR = "Неверное имя"

            fun canString(mode: String) : Boolean {
                try {
                    Mode.valueOf(mode.uppercase())
                    return true
                } catch (e: IllegalArgumentException) {
                    return false
                }
            }

            fun fromString(mode: String): Mode {
                try {
                    return valueOf(mode.uppercase())
                } catch (e: IllegalArgumentException) {
                    throw EnumException(NO_ENUM_ERROR)
                }
            }
        }
    }
}
