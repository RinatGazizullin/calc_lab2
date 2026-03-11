package ui.cli.render

import core.model.Expression
import ui.cli.basic.CanRender;

class ExpressionRender : CanRender<Expression> {
    override fun render(t: Expression): String {
        return "(${t.tokens.joinToString(", ")}) = ${t.body}"
    }
}
