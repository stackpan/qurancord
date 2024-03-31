package io.github.stackpan.qurancord.util.config

import java.awt.RenderingHints

val RENDERING_HINTS = mapOf(
    RenderingHints.KEY_STROKE_CONTROL to RenderingHints.VALUE_STROKE_NORMALIZE,
    RenderingHints.KEY_FRACTIONALMETRICS to RenderingHints.VALUE_FRACTIONALMETRICS_OFF,
    RenderingHints.KEY_INTERPOLATION to RenderingHints.VALUE_INTERPOLATION_BICUBIC,
    RenderingHints.KEY_RENDERING to RenderingHints.VALUE_RENDER_SPEED,
    RenderingHints.KEY_TEXT_ANTIALIASING to RenderingHints.VALUE_TEXT_ANTIALIAS_ON
)
