package com.example.lojasocial.ui.theme.usercontrol

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.lojasocial.data.models.HorarioFuncionamento
import com.example.lojasocial.data.repository.HorariosRepository
import com.example.lojasocial.ui.theme.appFontBold16
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FuncionamentoCard(id:String, data: String, numeroMaxVoluntarios: String, vagasDisponiveis: String, onClick: () -> Unit) {
    val vagas = vagasDisponiveis.toIntOrNull() ?: 0
    val cardColor = if (vagas == 0) Color(0xFFFFEBEE) else Color(0xFFE8F5E9)

    Card(
        modifier = Modifier
            .fillMaxWidth()
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