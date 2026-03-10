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
        override val description: String,
        val type: Manager.Mode,
    ) : HaveDescription {
        SIZE("size", "Задать количество НУ (нелинейных уравнений) в системе", Manager.Mode.SYSTEM),
        SOLVE_SYSTEM("solve", "Решить СНУ (систему нелинейных уравнений)", Manager.Mode.SYSTEM),
        BUILD("build", "Ввести новую СНУ (систему нелинейных уравнений)", Manager.Mode.SYSTEM),
        SHOW_SYSTEM("show", "Показать СНУ (систему нелинейных уравнений)", Manager.Mode.SYSTEM),
        SET_SYSTEM("set", "Ввести НУ (нелинейное уравнение)", Manager.Mode.SYSTEM),

        MAN("man", "Вывести мануал команды <command>", Manager.Mode.BOTH),
        EXIT("exit", "Завершить работу приложения", Manager.Mode.BOTH),
        HELP("help", "Вывести справку по командам", Manager.Mode.BOTH),
        SWITCH("switch", "Сменить режим (СНУ/НУ)", Manager.Mode.BOTH),

        SHOW_SINGLE("show", "Показать НУ (нелинейное уравнение)", Manager.Mode.SINGLE),
        SOLVE_SINGLE("solve", "Решить НУ (нелинейное уравнение)", Manager.Mode.SINGLE),
        SET_SINGLE("set", "Ввести НУ (нелинейное уравнение)", Manager.Mode.SINGLE);

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
