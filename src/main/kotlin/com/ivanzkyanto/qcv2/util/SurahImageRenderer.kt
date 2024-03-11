package com.ivanzkyanto.qcv2.util

import com.ivanzkyanto.qcv2.util.config.RENDERING_HINTS
import java.awt.Color
import java.awt.Font
import java.awt.font.TextLayout
import java.awt.image.BufferedImage

private const val MARGIN = 24
private const val FONT_SIZE = 72

private val ARABIC_FONT = Font("Lateef", Font.PLAIN, FONT_SIZE)

fun render(surah: String): BufferedImage {
    // Image dimension measurement
    val measurementImage = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
    val measurementGraphics = measurementImage.createGraphics()
    measurementGraphics.setRenderingHints(RENDERING_HINTS)

    val metrics = measurementGraphics.getFontMetrics(ARABIC_FONT)
    val imageWidth = metrics.stringWidth(surah) + MARGIN * 2
    val imageHeight = metrics.height + MARGIN * 2

    measurementGraphics.dispose()

    // Rendering
    val image = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)
    val graphics = image.createGraphics()
    graphics.setRenderingHints(RENDERING_HINTS)
    graphics.color = Color.WHITE
    graphics.fillRect(0, 0, image.width, image.height)
    graphics.color = Color.BLACK
    graphics.font = ARABIC_FONT

    val surahTextLayout = TextLayout(surah, ARABIC_FONT, graphics.fontRenderContext)
    surahTextLayout.draw(graphics, MARGIN.toFloat(), surahTextLayout.ascent + MARGIN)

    graphics.dispose()
    return image
}
