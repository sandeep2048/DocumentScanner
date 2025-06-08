package com.xsquare.documentscanner

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Rajath on 05/04/25.
 */

@HiltAndroidApp
class SnapDocsApp: Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize any libraries or components here
    }
}