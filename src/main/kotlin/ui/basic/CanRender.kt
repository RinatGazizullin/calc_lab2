package ui.cli.basic

interface CanRender<T> {
    fun render(t: T): String
}
