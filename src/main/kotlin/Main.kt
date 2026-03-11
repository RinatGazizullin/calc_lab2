import ui.cli.processor.CommandLineProcessor
import ui.cli.processor.InterfaceProcessor
import ui.gui.processor.GraphicProcessor

fun main(args: Array<String>) {
    if (args.isEmpty() || args[0] == "cli") {
        CommandLineProcessor(InterfaceProcessor()).start()
    } else {
        GraphicProcessor().start()
    }
}
