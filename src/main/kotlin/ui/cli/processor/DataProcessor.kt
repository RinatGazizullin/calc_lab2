package ui.cli.processor

import core.model.Expression
import core.processor.ExpressionProcessor
import ui.cli.basic.CanBuild
import ui.cli.basic.Command
import ui.cli.builder.ExpressionBuilder
import ui.cli.command.*

class DataProcessor(
    interfaceProcessor: InterfaceProcessor,
) {
    val commands: MutableMap<Command.Type, Command> = mutableMapOf()
    private val expressionProcessor = ExpressionProcessor()

    init {
        val builderExpression: CanBuild<Expression> = ExpressionBuilder(interfaceProcessor)

        commands[Command.Type.BUILD] = Build(expressionProcessor)
        commands[Command.Type.EXIT] = Exit()
        commands[Command.Type.HELP] = Help()
        commands[Command.Type.MAN] = Man(commands)
        commands[Command.Type.SET] = Set(expressionProcessor, builderExpression)
        commands[Command.Type.SHOW] = Show(expressionProcessor)
        commands[Command.Type.SIZE] = Size(expressionProcessor)
        commands[Command.Type.SOLVE_SINGLE] = SolveSingle(expressionProcessor)
        commands[Command.Type.SOLVE_SYSTEM] = SolveSystem(expressionProcessor)
    }
}
