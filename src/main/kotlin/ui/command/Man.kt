package ui.command

import ui.basic.Command
import ui.basic.HaveManual

class Man(private val commands: Map<Type, Command>) : Command(Type.MAN) {
    override val manual: String

    init {
        val builder = StringBuilder()
        builder.append(HaveManual.ManualBuilder.name(type.value))
        builder.append(HaveManual.ManualBuilder.description(type.description))
        builder.append(HaveManual.ManualBuilder.arguments(ARGUMENT_NAME, ARGUMENT_DESCRIPTION))
        manual = builder.toString()
    }

    companion object {
        private const val ARGS_COUNT = 1
        private const val ARGUMENT_NAME = "<command_name>"
        private const val TYPE_ERROR = "Команда <%s> не была обнаружена"
        private const val MODE_ERROR = "Команда <%s> недоступна в этом режиме"
        private const val NO_ARGS_ERROR = "Необходимо ввести один аргумент <command>"
        private const val ARGUMENT_DESCRIPTION = "Имя команды, для которого нужно описание"
    }

    override fun execute(arguments: Arguments): Result {
        if (arguments.args.size == ARGS_COUNT) {
            if (Type.canString(arguments.args[0])) {
                val type = Type.fromString(arguments.args[0])
                return if (commands.containsKey(type)) {
                    Result(commands[type]!!.manual, Result.Code.GOOD)
                } else {
                    Result(String.format(MODE_ERROR, arguments.args[0]), Result.Code.ERROR)
                }
            } else {
                return Result(String.format(TYPE_ERROR, arguments.args[0]), Result.Code.ERROR)
            }
        }
        return Result(NO_ARGS_ERROR, Result.Code.ERROR)
    }
}
