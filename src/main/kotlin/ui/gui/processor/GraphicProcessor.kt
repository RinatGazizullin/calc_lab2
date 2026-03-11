package ui.gui.processor

import ui.basic.Processor

class GraphicProcessor : Processor {
    private val dataProcessor = DataProcessor()

    override fun start() {
        ApplicationProcessor(dataProcessor.expressionProcessor, this).showWindow()
    }
}
