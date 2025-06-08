package com.xsquare.documentscanner.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.*
import com.xsquare.documentscanner.navigation.DSNavigation
import com.xsquare.documentscanner.ui.components.TopBar
import com.xsquare.documentscanner.ui.theme.DocumentScannerTheme
import com.xsquare.documentscanner.utils.LocalActivity
import com.xsquare.documentscanner.utils.LocalNavController
import com.xsquare.documentscanner.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the Mobile Ads SDK
        MobileAds.initialize(this) {}

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            DocumentScannerTheme {
                CompositionLocalProvider(
                    LocalActivity provides this@MainActivity,
                    LocalNavController provides navController
                ) {
                    Scaffold(
                        topBar = { TopBar() },
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            DSNavigation(viewModel)
                        }
                    }
                }
            }
        }
    }
}

