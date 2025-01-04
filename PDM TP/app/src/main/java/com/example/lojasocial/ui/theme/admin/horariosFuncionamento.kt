package com.example.lojasocial.ui.theme.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lojasocial.data.models.HorarioFuncionamento
import com.example.lojasocial.ui.viewmodels.HorariosViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.lojasocial.ui.theme.LojaSocialTheme
import androidx.compose.ui.tooling.preview.Preview
import com.example.lojasocial.ui.theme.bars.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HorariosFuncionamentoView(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HorariosViewModel = viewModel()
) {
    val errorMessage = viewModel.errorMessage

    TopBar(title = "Marcar Horário de Funcionamento", navController = navController)


    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // CALENDÁRIO
            HorariosCalendarView(viewModel = viewModel)

            // Exibe mensagem de erro
            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Erro: $it",
                    color = MaterialTheme.colorScheme.error
                )
            }

            // Botão para voltar (opcional)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("Voltar")
            }
        }
    }
}

@Composable
fun HorariosCalendarView(viewModel: HorariosViewModel) {
    val horariosList = viewModel.horariosList

    // Vamos criar um Calendar só para pegar o ano/mês inicial
    val initCalendar = Calendar.getInstance()
    val initYear = initCalendar.get(Calendar.YEAR)
    val initMonth = initCalendar.get(Calendar.MONTH)

    // Estados que controlam o mês e o ano exibidos
    var displayYear by remember { mutableStateOf(initYear) }
    var displayMonth by remember { mutableStateOf(initMonth) }

    // Crie um Calendar local para calcular quantos dias tem o mês e em que dia da semana começa
    val calendar = remember { Calendar.getInstance() }
    calendar.set(Calendar.YEAR, displayYear)
    calendar.set(Calendar.MONTH, displayMonth)
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    // Descobrir quantos dias tem neste mês/ano
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    // Dia da semana em que começa (1 = domingo, 2 = segunda, etc.)
    val startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    // Calcula offset para alinhar segunda-feira como 0 (ou outra lógica)
    val offset = (startDayOfWeek + 5) % 7

    // Título do calendário: ex: "Calendário de Agosto de 2025"
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botão "Mês anterior"
        IconButton(onClick = {
            // Decrementa o mês
            if (displayMonth == 0) {
                displayMonth = 11
                displayYear -= 1
            } else {
                displayMonth--
            }
        }) {
            Text("<")
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = "Calendário de ${getMonthName(displayMonth)} de $displayYear",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Botão "Próximo mês"
        IconButton(onClick = {
            // Incrementa o mês
            if (displayMonth == 11) {
                displayMonth = 0
                displayYear += 1
            } else {
                displayMonth++
            }
        }) {
            Text(">")
        }
    }

    // Cabeçalho dos dias da semana
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        listOf("Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom").forEach { diaSemana ->
            Text(text = diaSemana, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        }
    }

    // totalSlots = dias "vazios" + dias do mês
    val totalSlots = offset + daysInMonth
    // rowCount = quantas linhas (semanas) precisamos
    val rowCount = (totalSlots / 7) + if (totalSlots % 7 != 0) 1 else 0

    var dayCounter = 1

    // Constrói as linhas (semanas)
    for (week in 1..rowCount) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (dayOfWeek in 0..6) {
                val slotIndex = (week - 1) * 7 + dayOfWeek
                if (slotIndex < offset || slotIndex >= offset + daysInMonth) {
                    // Espaço vazio
                    Box(modifier = Modifier.weight(1f)) { /* Dia fora do mês atual */ }
                } else {
                    // Esse dia existe dentro do mês
                    val day = dayCounter
                    // Montamos um Calendar para esta data
                    val cal = Calendar.getInstance().apply {
                        set(Calendar.YEAR, displayYear)
                        set(Calendar.MONTH, displayMonth)
                        set(Calendar.DAY_OF_MONTH, day)
                    }
                    val dateString = calendarToString(cal)

                    // Ver se a data existe no Firestore
                    val horarioObj = isDateInList(dateString, horariosList)

                    // Mostra um box clicável
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .aspectRatio(1f) // quadrado
                            .clickable {
                                if (horarioObj == null) {
                                    // ainda não existe => adicionar
                                    viewModel.addHorario(dateString)
                                } else {
                                    // já existe => remover
                                    val idToRemove = horarioObj.id ?: ""
                                    viewModel.removeHorario(idToRemove)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        // Se existe, pintamos outra cor
                        if (horarioObj != null) {
                            Text(
                                text = "$day\n(Ativo)",
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(2.dp)
                            )
                        } else {
                            Text(
                                text = day.toString(),
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    dayCounter++
                }
            }
        }
    }
}

// Funções auxiliares:

fun getMonthName(monthIndex: Int): String {
    return listOf(
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    )[monthIndex]
}

// Retorna "yyyy-MM-dd"
fun calendarToString(calendar: Calendar): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return format.format(calendar.time)
}

// Retorna se a data "yyyy-MM-dd" consta na lista de HorariosFuncionamento
fun isDateInList(dateString: String, list: List<HorarioFuncionamento>): HorarioFuncionamento? {
    return list.firstOrNull { it.data == dateString }
}

// PREVIEW
@Preview(showBackground = true)
@Composable
fun HorariosViewPreview() {
    LojaSocialTheme {
        HorariosFuncionamentoView(navController = rememberNavController())
    }
}
