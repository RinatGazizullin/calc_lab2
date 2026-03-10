package ui.cli.processor

class InterfaceProcessor {
    fun readLine(message: String = ""): String {
        if (message.isNotEmpty()) renderMessage(message, false)
        return readln()
    }

    fun renderMessage(message: String, newLine: Boolean = true) {
        if (newLine)
            println(message)
        else
            print(message)
    }

    fun renderError(message: String) {
        println("Err: $message")
    }
}
