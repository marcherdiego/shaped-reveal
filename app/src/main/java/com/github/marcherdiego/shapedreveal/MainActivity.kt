package com.github.marcherdiego.shapedreveal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        Handler().postDelayed(
            {
                revealActivity(Intent(this, SecondActivity::class.java))
            },
            1000L
        )
    }
}