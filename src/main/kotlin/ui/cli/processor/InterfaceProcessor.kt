package ui.cli.processor

class InterfaceProcessor {
    fun readLine(): String {
        return readln()
    }

    fun renderMessage(message: String) {
        println(message)
    }

    fun renderError(message: String) {
        println("Err: $message")
    }
}
