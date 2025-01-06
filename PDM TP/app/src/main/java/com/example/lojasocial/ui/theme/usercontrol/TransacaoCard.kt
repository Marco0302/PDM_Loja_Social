package com.example.lojasocial.ui.theme.usercontrol

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransacaoCard(descricao: String, valor: Double, tipo: String, data: Long) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (tipo == "Entrada") Color(0xFFE8F5E9) else Color(0xFFFFEBEE) // Verde claro ou Vermelho claro
            ),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = tipo,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (tipo == "Entrada") Color(0xFF4CAF50) else Color(0xFFF44336)
                        )
                    )
                    Text(
                        text = descricao,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$valor €",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = convertDateToString(Date(data)),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
}

fun convertDateToString(date: Date, format: String = "dd/MM/yyyy"): String {
    val formatter = SimpleDateFormat(format, Locale.getDefault())
    return formatter.format(date)
}