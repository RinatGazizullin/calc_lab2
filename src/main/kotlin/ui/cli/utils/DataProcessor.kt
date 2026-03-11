package ui.cli.processor

import core.model.Border
import core.model.Expression
import core.processor.ExpressionProcessor
import core.solver.HalfSolver
import core.solver.IterationSolver
import core.solver.NewtonSolver
import core.solver.SimpleSystemSolver
import ui.cli.basic.CanBuild
import ui.cli.basic.Command
import ui.cli.builder.BorderBuilder
import ui.cli.builder.ExpressionBuilder
import ui.cli.command.*
import ui.cli.render.ExpressionRender
import ui.cli.render.ResultRender
import ui.cli.render.SystemRender

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
