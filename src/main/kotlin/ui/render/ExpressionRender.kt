package ui.render

import core.model.Expression
import ui.basic.CanRender;

class ExpressionRender : CanRender<Expression> {
    override fun render(t: Expression): String {
        return "(${t.tokens.joinToString(", ")}) = ${t.body}"
    }
}
