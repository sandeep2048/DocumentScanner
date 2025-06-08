package com.xsquare.documentscanner.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

/**
 * Created by Rajath on 23/03/25.
 */

@Composable
fun BannerAdView() {
    val context = LocalContext.current

    val adView = remember {
        AdView(context).apply {
            setAdSize(AdSize.BANNER) // Set correct banner size (50dp)
            adUnitId = "ca-app-pub-3940256099942544/6300978111" // Test AdMob ID (Use this first)
            loadAd(AdRequest.Builder().build())
        }
    }

    AndroidView(
        factory = { adView },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp) // Correct height for a banner ad
            .background(Color.Gray) // Just to visualize layout
    )
}