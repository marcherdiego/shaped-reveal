package com.github.marcherdiego.shapedreveal

import android.app.Activity
import android.content.Intent
import android.os.Bundle

fun Activity.revealActivity(intent: Intent, bundle: Bundle? = null) {
    startActivity(
        Intent(this, RevealActivity::class.java).apply {
            putExtra(RevealActivity.TARGET_INTENT, intent)
            putExtra(RevealActivity.TARGET_BUNDLE, bundle)
        }
    )
    overridePendingTransition(0, 0)
}
