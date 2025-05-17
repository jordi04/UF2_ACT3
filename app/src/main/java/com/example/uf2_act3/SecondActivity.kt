package com.example.uf2_act3

import com.example.uf2_act3.R
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout

class SecondActivity : AppCompatActivity() {

    private lateinit var motionLayout: MotionLayout
    private lateinit var animateButton: Button
    private lateinit var buttonClickSound: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        motionLayout = findViewById(R.id.motionLayout)
        animateButton = findViewById(R.id.animateButton)

        buttonClickSound = MediaPlayer.create(this, R.raw.button_click)
        buttonClickSound.setVolume(1.0f, 1.0f)
        //a veure si funciona
        animateButton.setOnClickListener {
            buttonClickSound.start()
            startAnimation()
        }

        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

            }

            override fun onTransitionCompleted(
                motionLayout: MotionLayout?,
                currentId: Int
            ) {
                if (currentId == R.id.end) {
                    val intent = Intent(this@SecondActivity, ThirdActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }
        })
    }

    private fun startAnimation() {
        motionLayout.transitionToEnd()
    }

    override fun onDestroy() {
        super.onDestroy()
        buttonClickSound.release()
    }
}