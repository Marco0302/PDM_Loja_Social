package com.example.lojasocial.ui.theme.usercontrol

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun InfoRow(icon: ImageVector?, text: String, estilo: TextStyle) {
    Row(verticalAlignment = Alignment.CenterVertically)
    {
        if (icon != null)
        {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF757575),
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            style = estilo,
            color = Color(0xFF333333)
        )
    }
}