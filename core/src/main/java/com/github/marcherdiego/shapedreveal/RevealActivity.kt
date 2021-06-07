package com.github.marcherdiego.shapedreveal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.marcherdiego.shapedreveal.widgets.CircularReveal

class RevealActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reveal)
        
        findViewById<CircularReveal>(R.id.circular_reveal).apply { 
            setOnCompletedListener { 
                finish()
            }
            startReveal()
        }
    }
}
