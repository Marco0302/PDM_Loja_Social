package com.example.lojasocial.ui.theme.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lojasocial.ui.theme.LojaSocialTheme
import com.example.lojasocial.ui.theme.login.LoginViewModel
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeJuntaView(navController: NavController, modifier: Modifier = Modifier) {
    val viewModelLogin = LoginViewModel()

    val userName = "Membro da Junta"

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 1) Cabeçalho (Top Section) ----------------------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // -- Parte Esquerda: Imagem e Nome --
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Circulo cinza (imagem placeholder)
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(LightGray)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            // -- Parte Direita: Botão Logout --
            Button(
                onClick = {
                    viewModelLogin.logout(
                        onLogoutSuccess = { navController.navigate("login") }
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = DarkGray)
            ) {
                Text(text = "Logout", color = Color.White)
            }
        }

        // 2) Coluna Central (Funções) ----------------------------------
        // Spacer(modifier = Modifier.height(20.dp))

        // Coluna com os botões
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { navController.navigate("estatisticas") }) {
                Text(text = "Relatórios Estatísticos")
            }
        }
    }
}

