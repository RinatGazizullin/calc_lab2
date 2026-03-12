package ui.basic

interface CanBuild<T, R> {
    companion object {
        const val MAX_COUNT = 3
    }

    fun build(data: R): T
}
