package ui.cli.basic

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
        BUILD("build", "Ввести новую СНУ (систему нелинейных уравнений)"),
        EXIT("exit", "Завершить работу приложения"),
        HELP("help", "Вывести справку по командам"),
        MAN("man", "Вывести мануал команды <command>"),
        SET("set", "Ввести НУ (нелинейное уравнение)"),
        SHOW("show", "Показать СНУ (систему нелинейных уравнений)"),
        SIZE("size", "Задать количество НУ (нелинейных уравнений) в системе"),
        SOLVE_SINGLE("equation", "Решить НУ (нелинейное уравнение)>"),
        SOLVE_SYSTEM("system", "Решить СНУ (систему нелинейных уравнений)");

        companion object {
            private const val NO_ENUM_ERROR = "Неверное имя"

            fun canString(mode: String) : Boolean {
                try {
                    Type.valueOf(mode.uppercase())
                    return true
                } catch (e: IllegalArgumentException) {
                    return false
                }
            }

            fun fromString(mode: String): Type {
                try {
                    return Type.valueOf(mode.uppercase())
                } catch (e: IllegalArgumentException) {
                    throw EnumException(NO_ENUM_ERROR)
                }
            }
        }
    }
}
