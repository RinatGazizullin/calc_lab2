package ui.render

import core.model.Result
import ui.basic.CanRender

class ResultRender: CanRender<Result> {
    override fun render(t: Result): String {
        return t.results.keys.joinToString("\n") { key -> "$key = ${t.results[key]}" }
    }
}
