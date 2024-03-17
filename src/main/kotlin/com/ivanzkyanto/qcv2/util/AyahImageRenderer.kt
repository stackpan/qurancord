package com.ivanzkyanto.qcv2.util

import com.ivanzkyanto.qcv2.util.config.RENDERING_HINTS
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.font.LineBreakMeasurer
import java.awt.font.TextAttribute
import java.awt.font.TextLayout
import java.awt.image.BufferedImage
import java.text.AttributedString

private const val IMAGE_WIDTH = 1280
private const val MARGIN = 32
private const val FONT_SIZE = 60
private const val LATIN_FONT_SIZE = (FONT_SIZE * 0.5).toInt()
private const val SECTION_GAP = (FONT_SIZE * 0.8).toInt()
private const val WRAPPING_WIDTH = (IMAGE_WIDTH - MARGIN * 2).toFloat()
private const val VERSE_LINE_GAP = 28f

private val ARABIC_FONT = Font("Katibeh", Font.PLAIN, FONT_SIZE)
private val LATIN_FONT = Font("Times New Roman", Font.PLAIN, LATIN_FONT_SIZE)

fun render(verse: String, translate: String, surahName: String, ayahNumber: Int): BufferedImage {
    val verseAttrString = AttributedString(verse)
    verseAttrString.addAttribute(TextAttribute.FONT, ARABIC_FONT)
    verseAttrString.addAttribute(TextAttribute.RUN_DIRECTION, TextAttribute.RUN_DIRECTION_RTL)

    val translateAttrString = AttributedString(translate)
    translateAttrString.addAttribute(TextAttribute.FONT, LATIN_FONT)

    val imageHeight = measureImageHeight(verseAttrString, translateAttrString)

    // Rendering
    val image = BufferedImage(IMAGE_WIDTH, imageHeight.toInt(), BufferedImage.TYPE_INT_ARGB)
    
    val graphics = image.createGraphics()
    graphics.setRenderingHints(RENDERING_HINTS)
    graphics.color = Color.WHITE
    graphics.fillRect(0, 0, image.width, image.height)
    graphics.color = Color.BLACK
    
    var dy = MARGIN.toFloat()

    graphics.font = ARABIC_FONT
    val verseMeasurer = LineBreakMeasurer(verseAttrString.iterator, graphics.fontRenderContext)
    verseMeasurer.position = verseAttrString.iterator.beginIndex
    dy = drawSection(graphics, verseMeasurer, verseAttrString.iterator.endIndex, dy, VERSE_LINE_GAP)

    graphics.font = LATIN_FONT
    val translateMeasurer = LineBreakMeasurer(translateAttrString.iterator, graphics.fontRenderContext)
    translateMeasurer.position = translateAttrString.iterator.beginIndex
    dy = drawSection(graphics, translateMeasurer, translateAttrString.iterator.endIndex, dy + SECTION_GAP)

    val qsTextLayout = TextLayout("[$surahName:$ayahNumber]", LATIN_FONT, graphics.fontRenderContext)
    qsTextLayout.draw(graphics, MARGIN.toFloat(), dy + SECTION_GAP)

    graphics.dispose()
    return image
}

private fun measureImageHeight(verseAttrString: AttributedString, translateAttrString: AttributedString): Float {
    val image = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)

    val graphics = image.createGraphics()
    graphics.setRenderingHints(RENDERING_HINTS)

    var imageHeight = (MARGIN * 2 + SECTION_GAP * 2).toFloat()

    graphics.font = ARABIC_FONT
    val verseMeasurer = LineBreakMeasurer(verseAttrString.iterator, graphics.fontRenderContext)
    verseMeasurer.position = verseAttrString.iterator.beginIndex
    imageHeight += measureSectionHeight(verseMeasurer, verseAttrString.iterator.endIndex, VERSE_LINE_GAP)

    graphics.font = LATIN_FONT
    val translateMeasurer = LineBreakMeasurer(translateAttrString.iterator, graphics.fontRenderContext)
    translateMeasurer.position = translateAttrString.iterator.beginIndex
    imageHeight += measureSectionHeight(translateMeasurer, translateAttrString.iterator.endIndex)

    graphics.dispose()
    return imageHeight
}

private fun measureSectionHeight(measurer: LineBreakMeasurer, iteratorEndIndex: Int, lineGap: Float = 0f): Int {
    var height = 0
    while (measurer.position < iteratorEndIndex) {
        val textLayout = measurer.nextLayout(WRAPPING_WIDTH)
        height += (textLayout.ascent + textLayout.descent + textLayout.leading + lineGap).toInt()
    }
    return height
}

private fun drawSection(graphics: Graphics2D, measurer: LineBreakMeasurer, iteratorEndIndex: Int, dy: Float, lineGap: Float = 0f): Float {
    var mutableDy = dy
    while (measurer.position < iteratorEndIndex) {
        val textLayout = measurer.nextLayout(WRAPPING_WIDTH)

        mutableDy += textLayout.ascent
        val dx = if (textLayout.isLeftToRight) MARGIN.toFloat() else (WRAPPING_WIDTH - textLayout.advance) + MARGIN

        textLayout.draw(graphics, dx, mutableDy)
        mutableDy += textLayout.descent + textLayout.leading + lineGap
    }
    return mutableDy
}
