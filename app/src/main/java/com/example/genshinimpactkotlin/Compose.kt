package com.example.genshinimpactkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color


import androidx.compose.ui.res.painterResource
import com.example.genshinimpactkotlin.ui.theme.GenshinImpactKotlinTheme

class Compose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GenshinImpactKotlinTheme {
                MyComponent()
            }
        }
    }
}

@Composable
fun MyComponent() {
    Row()
        {
            MyImage()
        }
}

@Composable
fun MyImage() {
    Image(
        painterResource(R.drawable.ic_launcher_foreground),
        "Mi imagen de prueba",
        modifier = Modifier.clip(CircleShape).background(Color.Gray)
    )
}
