package ui.gui.processor

import com.ezylang.evalex.Expression
import core.model.Border
import core.parser.ExpressionParser
import core.processor.ExpressionProcessor
import core.solver.HalfSolver
import core.solver.IterationSolver
import core.solver.NewtonSolver
import core.solver.SimpleSystemSolver
import ui.basic.CanBuild
import ui.basic.Command
import ui.command.*
import ui.gui.builder.BorderGuiBuilder
import ui.gui.builder.ExpressionGuiBuilder
import ui.render.ResultRender

class DataProcessor() {
    val commands: MutableMap<Command.Type, Command> = mutableMapOf()
    var border: Border? = null
    val expressionProcessor = ExpressionProcessor()
    val stateManager = StateManager(expressionProcessor)

    init {
        val expressionGuiBuilder: CanBuild<core.model.Expression, Pair<Int, Set<String>>> =
            ExpressionGuiBuilder(stateManager)

        val borderGuiBuilder = BorderGuiBuilder(expressionProcessor, stateManager)

        commands[Command.Type.EXAMPLE] = Example(expressionProcessor)
        commands[Command.Type.SET] = Set(expressionProcessor, expressionGuiBuilder)
        commands[Command.Type.SOLVE] = Solve(
            expressionProcessor, ResultRender(),
            borderGuiBuilder, listOf(HalfSolver(), IterationSolver(), NewtonSolver()),
            listOf(SimpleSystemSolver())
        )

        /*
        val borderBuilder = BorderGuiBuilder()

        commands[Command.Type.EXIT] = Exit()
        commands[Command.Type.HELP] = Help()
        commands[Command.Type.MAN] = Man(commands)
        commands[Command.Type.SHOW] = Show(expressionProcessor, SystemRender(ExpressionRender()))
         */
    }
}
