package ui.basic

import core.exception.EnumException

abstract class Command(val type: Type) : HaveManual {
    abstract fun execute(arguments: Arguments) : Result

    class Arguments(val args: List<String>)

    class Result(val message: String, val result: Code) {
        enum class Code {
            GOOD,
            ERROR,
            EXIT
        }
    }

    enum class Type(
        val value: String,
        override val description: String
    ) : HaveDescription {
        EXAMPLE("example", "Сменить НУ (нелинейное уравнение)"),
        EXIT("exit", "Завершить работу приложения"),
        HELP("help", "Вывести справку по командам"),
        MAN("man", "Вывести мануал команды <command>"),
        SET("set", "Ввести НУ (нелинейное уравнение)"),
        SHOW("show", "Показать СНУ (систему нелинейных уравнений)"),
        SIZE("size", "Задать количество НУ (нелинейных уравнений) в системе"),
        SOLVE("solve", "Решить СНУ (систему нелинейных уравнений), НУ (нелинейное уравнение)");

        companion object {
            private const val NO_ENUM_ERROR = "Неверное имя"

            fun canString(type: String) : Boolean {
                return Type.entries.find { entity -> entity.value == type.lowercase() } != null
            }

            fun fromString(type: String): Type {
                try {
                    return Type.entries.first { entity -> entity.value == type.lowercase() }
                } catch (e: IllegalArgumentException) {
                    throw EnumException(NO_ENUM_ERROR)
                }
            }
        }
    }
}
