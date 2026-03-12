package ui.cli.processor

import core.model.Border
import core.model.Expression
import core.processor.ExpressionProcessor
import core.solver.HalfSolver
import core.solver.IterationSolver
import core.solver.NewtonSolver
import core.solver.SimpleSystemSolver
import ui.basic.CanBuild
import ui.basic.Command
import ui.cli.builder.BorderBuilder
import ui.cli.builder.ExpressionBuilder
import ui.render.ExpressionRender
import ui.render.ResultRender
import ui.render.SystemRender
import ui.command.*
import ui.command.Set

class DataProcessor(
    interfaceProcessor: InterfaceProcessor,
) {
    val commands: MutableMap<Command.Type, Command> = mutableMapOf()
    val expressionProcessor = ExpressionProcessor()

    init {
        val builderExpression: CanBuild<Expression, Pair<Int, kotlin.collections.Set<String>>> = ExpressionBuilder(interfaceProcessor)
        val builderBorder: CanBuild<Border, kotlin.collections.Set<String>> = BorderBuilder(interfaceProcessor)

        commands[Command.Type.EXAMPLE] = Example(expressionProcessor)
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
