package com.example.uf2_act3

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var characterImageView: ImageView
    private lateinit var attackAButton: Button
    private lateinit var attackBButton: Button
    private lateinit var nextScreenButton: Button

    private lateinit var idleAnimation: AnimationDrawable
    private lateinit var attackAnimationA: AnimationDrawable
    private lateinit var attackAnimationB: AnimationDrawable

    private lateinit var backgroundMusic: MediaPlayer
    private lateinit var attackSoundA: MediaPlayer
    private lateinit var attackSoundB: MediaPlayer

    private var isAttacking = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterImageView = findViewById(R.id.characterImageView)
        attackAButton = findViewById(R.id.attackAButton)
        attackBButton = findViewById(R.id.attackBButton)
        nextScreenButton = findViewById(R.id.nextScreenButton)

        setupSounds()

        attackAButton.setOnClickListener {
            if (!isAttacking) {
                performAttackA()
            }
        }

        attackBButton.setOnClickListener {
            if (!isAttacking) {
                performAttackB()
            }
        }


        nextScreenButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        startIdleAnimation()
    }

    private fun setupSounds() {
        backgroundMusic = MediaPlayer.create(this, R.raw.background_music)
        backgroundMusic.isLooping = true

        attackSoundA = MediaPlayer.create(this, R.raw.attack_sound_a)
        attackSoundB = MediaPlayer.create(this, R.raw.attack_sound_b)
    }

    private fun startIdleAnimation() {
        characterImageView.setBackgroundResource(R.drawable.idle_animation)
        idleAnimation = characterImageView.background as AnimationDrawable
        idleAnimation.start()
    }

    private fun performAttackA() {
        isAttacking = true
        idleAnimation.stop()

        characterImageView.setBackgroundResource(R.drawable.attack_animation_a)
        attackAnimationA = characterImageView.background as AnimationDrawable
        attackAnimationA.start()
        attackSoundA.start()

        //calcular la duració total de l’animació
        val totalDuration = getAnimationDuration(attackAnimationA)
        characterImageView.postDelayed({
            startIdleAnimation()
            isAttacking = false
        }, totalDuration)
    }

    private fun performAttackB() {
        isAttacking = true
        idleAnimation.stop()

        characterImageView.setBackgroundResource(R.drawable.attack_animation_b)
        attackAnimationB = characterImageView.background as AnimationDrawable
        attackAnimationB.start()
        attackSoundB.start()

        val totalDuration = getAnimationDuration(attackAnimationB)
        characterImageView.postDelayed({
            startIdleAnimation()
            isAttacking = false
        }, totalDuration)
    }

    private fun getAnimationDuration(animation: AnimationDrawable): Long {
        var duration = 0
        for (i in 0 until animation.numberOfFrames) {
            duration += animation.getDuration(i)
        }
        return duration.toLong()
    }

    override fun onResume() {
        super.onResume()
        backgroundMusic.start()
    }

    override fun onPause() {
        super.onPause()
        backgroundMusic.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundMusic.release()
        attackSoundA.release()
        attackSoundB.release()
    }
}
