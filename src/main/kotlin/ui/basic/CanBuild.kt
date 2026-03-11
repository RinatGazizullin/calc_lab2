package ui.basic

interface CanBuild<T> {
    companion object {
        const val MAX_COUNT = 3
    }

    fun build(tokens: Set<String>): T
}
