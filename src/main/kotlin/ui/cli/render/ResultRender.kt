package ui.cli.render

import core.model.Result
import ui.cli.basic.CanRender

class ResultRender: CanRender<Result> {
    override fun render(t: Result): String {
        return t.results.keys.joinToString("\n") { key -> "$key = ${t.results[key]}" }
    }
}
