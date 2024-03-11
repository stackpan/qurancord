package com.ivanzkyanto.qcv2.util

import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.font.LineBreakMeasurer
import java.awt.font.TextAttribute
import java.awt.font.TextLayout
import java.awt.image.BufferedImage
import java.text.AttributedString

private val RENDERING_HINTS = mapOf(
    RenderingHints.KEY_ALPHA_INTERPOLATION to RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY,
    RenderingHints.KEY_ANTIALIASING to RenderingHints.VALUE_ANTIALIAS_ON,
    RenderingHints.KEY_COLOR_RENDERING to RenderingHints.VALUE_COLOR_RENDER_QUALITY,
    RenderingHints.KEY_DITHERING to RenderingHints.VALUE_DITHER_ENABLE,
    RenderingHints.KEY_STROKE_CONTROL to RenderingHints.VALUE_STROKE_PURE,
    RenderingHints.KEY_FRACTIONALMETRICS to RenderingHints.VALUE_FRACTIONALMETRICS_ON,
    RenderingHints.KEY_INTERPOLATION to RenderingHints.VALUE_INTERPOLATION_BILINEAR,
    RenderingHints.KEY_RENDERING to RenderingHints.VALUE_RENDER_QUALITY,
    RenderingHints.KEY_TEXT_ANTIALIASING to RenderingHints.VALUE_TEXT_ANTIALIAS_ON
)

private const val IMAGE_WIDTH = 1280
private const val MARGIN = 18
private const val FONT_SIZE = 60
private const val LATIN_FONT_SIZE = (FONT_SIZE * 0.5).toInt()
private const val SECTION_GAP = (FONT_SIZE * 0.8).toInt()
private const val WRAPPING_WIDTH = (IMAGE_WIDTH - MARGIN * 2).toFloat()

private val ARABIC_FONT = Font("Lateef", Font.PLAIN, FONT_SIZE)
private val LATIN_FONT = Font("Times New Roman", Font.PLAIN, LATIN_FONT_SIZE)

fun render(arabic: String, translate: String, surahName: String, ayahNumber: Int): BufferedImage {
    val arabicAttrString = AttributedString(arabic)
    arabicAttrString.addAttribute(TextAttribute.FONT, ARABIC_FONT)
    arabicAttrString.addAttribute(TextAttribute.RUN_DIRECTION, TextAttribute.RUN_DIRECTION_RTL)

    val translateAttrString = AttributedString(translate)
    translateAttrString.addAttribute(TextAttribute.FONT, LATIN_FONT)

    val imageHeight = measureImageHeight(arabicAttrString, translateAttrString)

    // Rendering
    val image = BufferedImage(IMAGE_WIDTH, imageHeight, BufferedImage.TYPE_INT_ARGB)
    
    val graphics = image.createGraphics()
    graphics.setRenderingHints(RENDERING_HINTS)
    graphics.color = Color.WHITE
    graphics.fillRect(0, 0, image.width, image.height)
    graphics.color = Color.BLACK
    
    var dy = MARGIN.toFloat()

    graphics.font = ARABIC_FONT
    val arabicMeasurer = LineBreakMeasurer(arabicAttrString.iterator, graphics.fontRenderContext)
    arabicMeasurer.position = arabicAttrString.iterator.beginIndex
    dy = drawSection(graphics, arabicMeasurer, arabicAttrString.iterator.endIndex, dy)

    graphics.font = LATIN_FONT
    val translateMeasurer = LineBreakMeasurer(translateAttrString.iterator, graphics.fontRenderContext)
    translateMeasurer.position = translateAttrString.iterator.beginIndex
    dy = drawSection(graphics, translateMeasurer, translateAttrString.iterator.endIndex, dy + SECTION_GAP)

    val qsTextLayout = TextLayout("[$surahName:$ayahNumber]", LATIN_FONT, graphics.fontRenderContext)
    qsTextLayout.draw(graphics, MARGIN.toFloat(), dy + SECTION_GAP)

    graphics.dispose()
    return image
}

private fun measureImageHeight(arabicAttrString: AttributedString, translateAttrString: AttributedString): Int {
    val image = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)

    val graphics = image.createGraphics()
    graphics.setRenderingHints(RENDERING_HINTS)

    var imageHeight = MARGIN * 2 + SECTION_GAP * 2

    graphics.font = ARABIC_FONT
    val arabicMeasurer = LineBreakMeasurer(arabicAttrString.iterator, graphics.fontRenderContext)
    arabicMeasurer.position = arabicAttrString.iterator.beginIndex
    imageHeight += measureSectionHeight(arabicMeasurer, arabicAttrString.iterator.endIndex)

    graphics.font = LATIN_FONT
    val translateMeasurer = LineBreakMeasurer(translateAttrString.iterator, graphics.fontRenderContext)
    translateMeasurer.position = translateAttrString.iterator.beginIndex
    imageHeight += measureSectionHeight(translateMeasurer, translateAttrString.iterator.endIndex)

    graphics.dispose()
    return imageHeight
}

private fun measureSectionHeight(measurer: LineBreakMeasurer, iteratorEndIndex: Int): Int {
    var height = 0
    while (measurer.position < iteratorEndIndex) {
        val textLayout = measurer.nextLayout(WRAPPING_WIDTH)
        height += (textLayout.ascent + textLayout.descent + textLayout.leading).toInt()
    }
    return height
}

private fun drawSection(graphics: Graphics2D, measurer: LineBreakMeasurer, iteratorEndIndex: Int, dy: Float): Float {
    var mutableDy = dy
    while (measurer.position < iteratorEndIndex) {
        val textLayout = measurer.nextLayout(WRAPPING_WIDTH)

        mutableDy += textLayout.ascent
        val dx = if (textLayout.isLeftToRight) MARGIN.toFloat() else (WRAPPING_WIDTH - textLayout.advance) + MARGIN

        textLayout.draw(graphics, dx, mutableDy)
        mutableDy += textLayout.descent + textLayout.leading
    }
    return mutableDy
}
