package ru.surftudio.practice_customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.core.content.withStyledAttributes
import kotlin.math.min
import kotlin.random.Random

class CustomView(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {
    var drawingFigure = 0
    var pointX = 0F
    var pointY = 0F
    private var radius = 0F
    var center = PointF(0F, 0F)
    private var circle = RectF(0F, 0F, 0F, 0F)

    private var lineWith = AndroidUtils.dp(context, 5F).toFloat()
    private var fontSize = AndroidUtils.dp(context, 20F).toFloat()
    private var colors = emptyList<Int>()
    private val shapes = mutableListOf<Shape>()

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = lineWith
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = lineWith
        strokeCap = Paint.Cap.SQUARE
        strokeJoin = Paint.Join.MITER
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = fontSize
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.CustomView) {
            lineWith = getDimension(R.styleable.CustomView_lineWidth, lineWith)
            fontSize = getDimension(R.styleable.CustomView_fontSize, fontSize)
            colors = listOf(
                getColor(
                    R.styleable.CustomView_color1,
                    randomColor()
                ),
                getColor(
                    R.styleable.CustomView_color2,
                    randomColor()
                ),
                getColor(
                    R.styleable.CustomView_color3,
                    randomColor()
                ),
                getColor(
                    R.styleable.CustomView_color4,
                    randomColor()
                ),
                getColor(
                    R.styleable.CustomView_color5,
                    randomColor()
                ),
                getColor(
                    R.styleable.CustomView_color6,
                    randomColor()
                ),
                getColor(
                    R.styleable.CustomView_color7,
                    randomColor()
                )
            )
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = min(w, h) / 24F - lineWith / 24
        center = PointF(w / 8F, h / 8F)
        circle = RectF(
            center.x - radius, center.y - radius,
            center.x + radius, center.y + radius
        )
    }

    fun drawFigure(drawingFigure: Int) {
        this.drawingFigure = drawingFigure
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        val index = (Math.random() * 7 + 1).toInt()
        paint.color = colors.getOrNull(index) ?: R.color.green
        circlePaint.color = colors.getOrNull(index) ?: R.color.green

        saveShape(canvas)

        canvas.drawText(
            "Количество фигур: " + shapes.size.toString(),
            500F,
            50F + textPaint.textSize / 2,
            textPaint,
        )

        if (shapes.size >= 10) {
            Toast.makeText(this.context, "Game Over", Toast.LENGTH_LONG).show()
            canvas.drawColor(resources.getColor(R.color.black))
        }
    }

    private fun saveShape(canvas: Canvas) {
        val randomX = (Math.random() * 6 + 1).toFloat()
        val randomY = (Math.random() * 4 + 1).toFloat()
        when (drawingFigure) {
            1 -> {
                val shapeRadius = radius * randomX
                center = PointF(pointX , pointY)
                val shape = Shape(
                    id = 1,
                    figure = RectF(
                        center.x - shapeRadius, center.y - shapeRadius,
                        center.x + shapeRadius, center.y + shapeRadius
                    ),
                    color = paint.color
                )
                shapes.add(shape)
            }
            2 -> {
                val shape = Shape(
                    id = 2,
                    figure = RectF(pointX, pointY, pointX / randomX, pointY * randomY),
                    color = paint.color
                )
                shapes.add(shape)
            }
            3 -> {
                val shape = Shape(
                    id = 3,
                    figure = RectF(pointX, pointY, pointX / randomX, pointX * randomY),
                    color = paint.color
                )
                shapes.add(shape)
            }
        }
        drawShape(canvas)
    }

    private fun drawShape(canvas: Canvas) {
        shapes.map { shape ->
            paint.color = shape.color
            circlePaint.color = shape.color
            when (shape.id) {
                1 -> {
                    canvas.drawOval(shape.figure, circlePaint)
                }
                2 -> {
                    canvas.drawRect(shape.figure, paint)
                }
                3 -> {
                    canvas.drawRoundRect(shape.figure, 50F, 50F, paint)
                }
                else -> return@map
            }
        }
    }

    private fun randomColor() = Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())
}