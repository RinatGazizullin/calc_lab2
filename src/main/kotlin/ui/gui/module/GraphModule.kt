package ui.gui.module

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import core.basic.Subscriber
import core.processor.ExpressionProcessor
import ui.gui.processor.StateManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.BasicStroke
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.math.BigDecimal
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.round

class GraphModule(
    private val expressionProcessor: ExpressionProcessor,
    private val stateManager: StateManager
) : Subscriber {
    private val trigger = mutableStateOf(false)

    init {
        stateManager.subscribe(this)
    }

    override fun changed() {
        trigger.value = !trigger.value
    }

    @Composable
    fun content() {
        val currentTrigger = trigger.value
        var plotBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
        var isComputing by remember { mutableStateOf(false) }

        val varX = stateManager.tokens[0]
        val varY = stateManager.tokens[1]
        val bordersX = stateManager.borders[varX] ?: Pair("-5", "5")
        val bordersY = stateManager.borders[varY] ?: Pair("-5", "5")

        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            val density = LocalDensity.current
            val widthPx = with(density) { maxWidth.toPx().toInt() }.coerceAtLeast(100)
            val heightPx = with(density) { maxHeight.toPx().toInt() }.coerceAtLeast(100)

            LaunchedEffect(currentTrigger, widthPx, heightPx) {
                isComputing = true
                plotBitmap = withContext(Dispatchers.Default) {
                    renderToImage(varX, varY, bordersX, bordersY, widthPx, heightPx)
                }
                isComputing = false
            }

            plotBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            if (isComputing) {
                CircularProgressIndicator(
                    color = Color(0xFF228B22),
                    strokeWidth = 4.dp,
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
            }
        }
    }

    private suspend fun renderToImage(
        varX: String, varY: String,
        bX: Pair<String, String>, bY: Pair<String, String>,
        width: Int, height: Int
    ): ImageBitmap = withContext(Dispatchers.Default) {
        val img = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val g = img.createGraphics()

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.color = java.awt.Color.WHITE
        g.fillRect(0, 0, width, height)

        var xMin = bX.first.toDoubleOrNull() ?: -5.0
        xMin -= abs(xMin)

        val xMax = (bX.second.toDoubleOrNull() ?: 5.0) * 2
        var yMin = bY.first.toDoubleOrNull() ?: -5.0
        yMin -= abs(yMin)

        val yMax = (bY.second.toDoubleOrNull() ?: 5.0) * 2
        val rangeX = xMax - xMin
        val rangeY = yMax - yMin

        drawGridAndAxes(g, width, height, xMin, xMax, yMin, yMax, rangeX, rangeY, varX, varY)

        val threshold = 0.02
        val firstColor = java.awt.Color(255, 0, 0).rgb
        val secondColor = java.awt.Color(0, 150, 255).rgb

        val jobs = (0 until height).map { py ->
            val mathY = yMin + ((height - py).toDouble() / height) * rangeY
            val bdY = BigDecimal.valueOf(mathY)

            for (px in 0 until width) {
                val mathX = xMin + (px.toDouble() / width) * rangeX
                val bdX = BigDecimal.valueOf(mathX)
                val params = mapOf(varX to bdX, varY to bdY)

                try {
                    if (expressionProcessor.size > 0) {
                        val z = expressionProcessor.calculateByIndex(params, 0).toDouble()
                        if (abs(z) < threshold) {
                            img.setRGB(px, py, firstColor)
                        }
                    }
                    if (expressionProcessor.size > 1) {
                        val z2 = expressionProcessor.calculateByIndex(params, 1).toDouble()
                        if (abs(z2) < threshold) {
                            img.setRGB(px, py, secondColor)
                        }
                    }
                } catch (_: Exception) {
                }
            }
        }

        g.dispose()
        img.toComposeImageBitmap()
    }

    private fun drawGridAndAxes(
        g: java.awt.Graphics2D,
        width: Int,
        height: Int,
        xMin: Double,
        xMax: Double,
        yMin: Double,
        yMax: Double,
        rangeX: Double,
        rangeY: Double,
        labelX: String,
        labelY: String
    ) {
        val gridStep = gridStep(rangeX)

        val fontSize = 20
        val tickFont = java.awt.Font("Monospaced", java.awt.Font.PLAIN, fontSize)
        val labelFont = java.awt.Font("Monospaced", java.awt.Font.PLAIN, fontSize * 2)
        val labelMetrics = g.getFontMetrics(labelFont)

        val zeroX = ((0.0 - xMin) / rangeX * width).toInt()
        val zeroY = (height - ((0.0 - yMin) / rangeY * height)).toInt()

        val axisXPos = zeroX.coerceIn(10, width - 50)
        val axisYPos = zeroY.coerceIn(20, height - 10)
        g.font = tickFont

        var currX = ceil(xMin / gridStep) * gridStep
        while (currX <= xMax) {
            val sx = ((currX - xMin) / rangeX * width).toInt()

            g.color = java.awt.Color(235, 235, 235)
            g.stroke = BasicStroke(1f)
            g.drawLine(sx, 0, sx, height)

            if (abs(currX) > 1e-9) {
                g.color = java.awt.Color.BLACK
                val label = formatValue(currX)
                g.drawString(label, sx + 4, axisYPos + fontSize + 4)
            }
            currX += gridStep
        }

        var currY = ceil(yMin / gridStep) * gridStep
        while (currY <= yMax) {
            val sy = (height - ((currY - yMin) / rangeY * height)).toInt()

            g.color = java.awt.Color(235, 235, 235)
            g.stroke = BasicStroke(1f)
            g.drawLine(0, sy, width, sy)

            if (abs(currY) > 1e-9) {
                g.color = java.awt.Color.BLACK
                val label = formatValue(currY)
                g.drawString(label, axisXPos + 4, sy + fontSize + 4)
            }
            currY += gridStep
        }

        g.color = java.awt.Color.LIGHT_GRAY
        g.stroke = BasicStroke(2f)

        if (0.0 in xMin..xMax) g.drawLine(zeroX, 0, zeroX, height)
        if (0.0 in yMin..yMax) g.drawLine(0, zeroY, width, zeroY)

        g.color = java.awt.Color.BLACK
        g.drawString("0", axisXPos + 4, axisYPos + fontSize + 4)

        g.font = labelFont
        g.color = java.awt.Color.BLACK

        g.drawString(
            labelX.uppercase(),
            width - labelMetrics.stringWidth(labelX) - 8,
            axisYPos - 4
        )

        g.drawString(
            labelY.uppercase(),
            axisXPos - labelMetrics.stringWidth(labelY) - 8,
            fontSize * 2 - 4
        )
    }

    private fun formatValue(value: Double): String {
        return if (value == value.toLong().toDouble()) {
            value.toLong().toString()
        } else {
            "%.2f".format(value).replace(',', '.').trimEnd('0').trimEnd('.')
        }
    }

    private fun gridStep(range: Double): Double {
        val absRange = abs(range)
        return when {
            absRange <= 2.0 -> 0.5
            absRange <= 10.0 -> 1.0
            absRange <= 20.0 -> 1.0
            absRange <= 50.0 -> 5.0
            else -> 10.0
        }
    }
}
