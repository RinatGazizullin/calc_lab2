package ui.cli.basic

interface CanBuild<T> {
    companion object {
        const val MAX_COUNT = 3
    }

    fun build(size: Int): T
}
