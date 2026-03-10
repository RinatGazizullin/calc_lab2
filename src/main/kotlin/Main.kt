import ui.cli.processor.CommandLineProcessor
import ui.cli.processor.InterfaceProcessor

fun main() {
    val interfaceProcessor = InterfaceProcessor()
    CommandLineProcessor(interfaceProcessor).start()
}
