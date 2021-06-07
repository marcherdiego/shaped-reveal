package com.github.marcherdiego.shapedreveal

import android.app.Activity
import android.content.Intent
import android.os.Bundle

fun Activity.revealActivity(intent: Intent, bundle: Bundle? = null) {
    startActivity(intent, bundle)
    startActivity(Intent(this, RevealActivity::class.java))
}
