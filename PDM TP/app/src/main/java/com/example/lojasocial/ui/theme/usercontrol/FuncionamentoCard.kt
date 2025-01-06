package com.example.lojasocial.ui.theme.usercontrol

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lojasocial.ui.theme.appFontBold16


@Composable
fun FuncionamentoCard(navController: NavController, id:String, data: String, numeroMaxVoluntarios: String, vagasDisponiveis: String, onClick: () -> Unit) {
    val vagas = vagasDisponiveis.toIntOrNull() ?: 0
    val cardColor = if (vagas == 0) Color(0xFFFFEBEE) else Color(0xFFE8F5E9)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick () }
            .padding(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .background(cardColor),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFFE0E0E0), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "",
                    tint = Color(0xFF757575),
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            ) {
                InfoRow(icon = null, text = data, estilo = appFontBold16)
                InfoRow(
                    icon = null,
                    text = "nº vagas: $numeroMaxVoluntarios",
                    estilo = MaterialTheme.typography.bodyMedium
                )
                InfoRow(
                    icon = null,
                    text = "nº disponíveis: $vagasDisponiveis",
                    estilo = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}