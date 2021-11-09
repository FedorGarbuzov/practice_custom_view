package ru.surftudio.practice_customview

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<CustomView>(R.id.custom_view)

        view.setOnTouchListener { v, event ->
            view.pointX = event.x
            view.pointY = event.y
            val drawingFigure = (Math.random() * 3 + 1).toInt()
            if (event.action == MotionEvent.ACTION_DOWN) {
                view.drawFigure(drawingFigure)
            }
            true
        }
    }
}