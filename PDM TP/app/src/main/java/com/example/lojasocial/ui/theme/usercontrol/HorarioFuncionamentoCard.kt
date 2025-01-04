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
import androidx.navigation.compose.rememberNavController
import com.example.lojasocial.data.models.HorarioFuncionamento
import com.example.lojasocial.ui.theme.appFontBold16
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun HorarioFuncionamentoCard(data: String, numeroMaxVoluntarios: String) {
    var itemCount by remember { mutableIntStateOf(0) }
    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .background(Color(0xFFF5F5F5)),
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
                    contentDescription = "List Icon",
                    tint = Color(0xFF757575),
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.weight(1f)
            )
            {
                InfoRow(icon = null, text = data, estilo = appFontBold16)
                InfoRow(
                    icon = null,
                    text = "nº máx de voluntários: $numeroMaxVoluntarios",
                    estilo = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(contentAlignment = Alignment.TopEnd) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options",
                    modifier = Modifier
                        .clickable { menuExpanded = true }
                        .padding(8.dp)
                )
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {

                    DropdownMenuItem(
                        text = { Text("novo agregado") },
                        onClick = {
                            //navController.navigate("addAgregadoFamiliar/$id")
                            menuExpanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text("nova visita") },
                        onClick = {
                            //navController.navigate("addNewItem/$id")
                            menuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("editar") },
                        onClick = {
                            //navController.navigate("editBeneficiario/$id")
                            menuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("pedidos") },
                        onClick = {
                            //navController.navigate("shareList/$id")
                            menuExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHorarioFuncionamento(){
    //navController = rememberNavController()
    HorarioFuncionamentoCard(data = "2025-01-17", numeroMaxVoluntarios = "8")
}