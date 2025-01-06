package com.example.lojasocial.ui.theme.usercontrol

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TransactionTypeSelector(
    selectedType: String,
    onTypeChange: (String) -> Unit
) {
    val options = listOf("Entrada", "Saída")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        options.forEach { type ->
            val isSelected = type == selectedType
            Button(
                onClick = { onTypeChange(type) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = when {
                        isSelected && type == "Entrada" -> Color(0xFF4CAF50)
                        isSelected && type == "Saída" -> Color(0xFFF44336)
                        else -> Color(0xFFF1F1F1)
                    },
                    contentColor = if (isSelected) Color.White else Color.Black
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
            ) {
                Text(
                    text = type,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}