package com.github.marcherdiego.shapedreveal

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.marcherdiego.shapedreveal.widgets.CircularReveal

class RevealActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.decorView?.setPadding(0, 0, 0, 0)
        window?.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_reveal)
        supportActionBar?.hide()

        val targetIntent = intent?.getParcelableExtra<Intent>(TARGET_INTENT)
        val targetBundle = intent?.getParcelableExtra<Bundle>(TARGET_BUNDLE)

        findViewById<CircularReveal>(R.id.circular_reveal).apply {
            setOnRippleCompletedListener {
                startActivity(targetIntent, targetBundle)
                startActivity(intent)
            }
            setOnCompletedListener {
                finish()
            }
            startReveal()
        }
    }

    override fun onPause() {
        overridePendingTransition(0, 0)
        super.onPause()
    }

    companion object {
        const val TARGET_INTENT = "target_intent"
        const val TARGET_BUNDLE = "target_bundle"
    }
}
