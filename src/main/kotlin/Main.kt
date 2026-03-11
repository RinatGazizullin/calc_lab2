import ui.cli.processor.CommandLineProcessor
import ui.cli.processor.InterfaceProcessor
import ui.gui.processor.ApplicationProcessor

fun main(args: Array<String>) {
    if (args.isEmpty() || args[0] == "cli") {
        val interfaceProcessor = InterfaceProcessor()
        CommandLineProcessor(interfaceProcessor).start()
    } else {
        ApplicationProcessor().showWindow()
    }
}
