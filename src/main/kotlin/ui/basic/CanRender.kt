package ui.basic

interface CanRender<T> {
    fun render(t: T): String
}
