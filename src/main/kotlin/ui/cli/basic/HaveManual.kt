package ui.cli.basic

interface HaveManual {
    val manual: String

    class ManualBuilder {
        companion object {
            fun name(name: String): String {
                return "Название команды - ${name}\n\n"
            }

            fun description(description: String): String {
                return "Описание команды - ${description}\n\n"
            }

            fun arguments(argument: String, description: String): String {
                return "Команда требует аргументы - $argument: $description";
            }

            fun someArguments(argument: String, description: String): String {
                return "Команда принимает аргументы - $argument: $description";
            }

            fun noArguments(): String {
                return "Команда не принимает никакие аргументы"
            }
        }
    }
}
