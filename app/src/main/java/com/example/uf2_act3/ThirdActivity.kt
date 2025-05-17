package com.example.uf2_act3

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout

class ThirdActivity : AppCompatActivity() {

    private lateinit var toggleButton: Button
    private lateinit var scoreTextView: TextView
    private lateinit var motionLayout: MotionLayout
    private lateinit var buttonClickSound: MediaPlayer

    private var score = 0
    private var isAtStart = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        toggleButton = findViewById(R.id.toggleButton)
        scoreTextView = findViewById(R.id.scoreTextView)
        motionLayout = findViewById(R.id.motionLayout)

        buttonClickSound = MediaPlayer.create(this, R.raw.button_click)
        //segueix sent molt baix encara que estigui al màxim
        buttonClickSound.setVolume(1.0f, 1.0f)
        updateScore()

        toggleButton.setOnClickListener {
            buttonClickSound.start()
            if (isAtStart) {
                motionLayout.transitionToEnd()
            } else {
                motionLayout.transitionToStart()
            }
            isAtStart = !isAtStart
        }

        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(motionLayout: MotionLayout, startId: Int, endId: Int) {}

            override fun onTransitionChange(
                motionLayout: MotionLayout,
                startId: Int,
                endId: Int,
                progress: Float
            ) {}

            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                incrementScore()
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {}
        })
    }

    private fun incrementScore() {
        score++
        updateScore()
    }

    private fun updateScore() {
        scoreTextView.text = buildString {
        append("Puntuació: ")
        append(score)
    }
    }

    override fun onDestroy() {
        super.onDestroy()
        buttonClickSound.release()
    }
}
