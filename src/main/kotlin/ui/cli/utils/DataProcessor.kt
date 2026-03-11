package ui.cli.utils

import core.model.Border
import core.model.Expression
import core.processor.ExpressionProcessor
import core.solver.HalfSolver
import core.solver.IterationSolver
import core.solver.NewtonSolver
import core.solver.SimpleSystemSolver
import ui.basic.CanBuild
import ui.basic.Command
import ui.builder.BorderBuilder
import ui.builder.ExpressionBuilder
import ui.cli.processor.InterfaceProcessor
import ui.render.ExpressionRender
import ui.render.ResultRender
import ui.render.SystemRender
import ui.command.*
import ui.command.Set

class DataProcessor(
    interfaceProcessor: InterfaceProcessor,
) {
    val commands: MutableMap<Command.Type, Command> = mutableMapOf()
    private val expressionProcessor = ExpressionProcessor()

    init {
        val builderExpression: CanBuild<Expression> = ExpressionBuilder(interfaceProcessor)
        val builderBorder: CanBuild<Border> = BorderBuilder(interfaceProcessor)

        commands[Command.Type.BUILD] = Build(expressionProcessor)
        commands[Command.Type.EXIT] = Exit()
        commands[Command.Type.HELP] = Help()
        commands[Command.Type.MAN] = Man(commands)
        commands[Command.Type.SET] = Set(expressionProcessor, builderExpression)
        commands[Command.Type.SHOW] = Show(expressionProcessor, SystemRender(ExpressionRender()))
        commands[Command.Type.SOLVE] = Solve(expressionProcessor, ResultRender(),
            builderBorder, listOf(HalfSolver(), IterationSolver(), NewtonSolver()),
            listOf(SimpleSystemSolver()))
    }
}
