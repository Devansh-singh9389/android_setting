package com.example.androidtvsetting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast

/**
 * Lightweight Activity designed for Android TV devices to bypass custom OEM TV Settings
 * and explicitly open the hidden stock Android Settings (com.android.settings).
 */
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        openNativeAndroidSettings()
        // Instantly finish this activity so user returns to TV Launcher when exiting settings
        finish()
    }

    private fun openNativeAndroidSettings() {
        val intentsToTry = arrayOf(
            // 1. Android TV Leanback Settings (Main Settings Activity)
            Intent().apply {
                setClassName("com.android.tv.settings", "com.android.tv.settings.MainSettings")
            },
            // 2. Android TV Device Info / About
            Intent().apply {
                setClassName("com.android.tv.settings", "com.android.tv.settings.deviceinfo.AboutActivity")
            },
            // 3. Generic TV Settings Action
            Intent("android.settings.TV_SETTINGS").apply {
                addCategory(Intent.CATEGORY_DEFAULT)
            },
            // 4. Fallback to standard stock settings package components
            Intent().apply {
                setClassName("com.android.settings", "com.android.settings.Settings")
            },
            packageManager.getLaunchIntentForPackage("com.android.tv.settings"),
            packageManager.getLaunchIntentForPackage("com.android.settings")
        )

        for (intent in intentsToTry) {
            if (intent != null) {
                try {
                    // Ensure it runs as a fresh top-level task
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                    Log.d("ANDROIDTVSetting", "Successfully opened settings using: $intent")
                    return
                } catch (e: Exception) {
                    Log.w("ANDROIDTVSetting", "Intent failed: $intent", e)
                }
            }
        }

        Toast.makeText(this, "Could not open stock Android Settings on this device", Toast.LENGTH_LONG).show()
    }
}
