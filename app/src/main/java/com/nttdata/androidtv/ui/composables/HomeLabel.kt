package com.nttdata.androidtv.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text

@Composable
fun HomeLabel(text: String, topDp: Dp = 40.dp) {
    Spacer(modifier = Modifier.height(topDp))

    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(horizontal = 40.dp)
    )

    //Spacer(modifier = Modifier.height(10.dp))
}