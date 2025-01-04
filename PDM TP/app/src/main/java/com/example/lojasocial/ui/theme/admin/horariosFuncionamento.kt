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


        }
    }
}

@Composable
fun HorariosCalendarView(viewModel: HorariosViewModel) {
    val horariosList = viewModel.horariosList

    // ---------------------
    // ESTADO DO CALENDÁRIO
    // ---------------------
    // Vamos inicializar com o ano e mês atual
    val initCalendar = Calendar.getInstance()
    val initYear = initCalendar.get(Calendar.YEAR)
    val initMonth = initCalendar.get(Calendar.MONTH)

    // Mês/ano em exibição
    var displayYear by remember { mutableStateOf(initYear) }
    var displayMonth by remember { mutableStateOf(initMonth) }

    // --------------------------
    // ESTADO DO DIÁLOGO
    // --------------------------
    // Mostra ou não o AlertDialog
    var showDialog by remember { mutableStateOf(false) }
    // Guarda a data que vamos salvar quando usuário confirmar no diálogo
    var dateToAdd by remember { mutableStateOf("") }

    // Cria um Calendar local para calcular as infos do mês
    val calendar = remember { Calendar.getInstance() }
    calendar.set(Calendar.YEAR, displayYear)
    calendar.set(Calendar.MONTH, displayMonth)
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    // Número de dias no mês atual
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    // Dia da semana em que começa (1=domingo, 2=segunda...)
    val startDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    // Offset para alinhar a segunda-feira como 0 (ajuste como preferir)
    val offset = (startDayOfWeek + 5) % 7

    // --------------
    // TÍTULO + BOTÕES DE NAVEGAÇÃO
    // --------------
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Botão "Mês anterior"
        IconButton(onClick = {
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
            Text(
                text = diaSemana,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }

    // Total de "slots" = offset + daysInMonth
    val totalSlots = offset + daysInMonth
    // Quantas linhas (semanas) precisamos
    val rowCount = (totalSlots / 7) + if (totalSlots % 7 != 0) 1 else 0

    var dayCounter = 1

    // --------------
    // MONTANDO O GRID DE DIAS
    // --------------
    for (week in 1..rowCount) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (dayOfWeek in 0..6) {
                val slotIndex = (week - 1) * 7 + dayOfWeek

                // Se ainda não chegamos ao primeiro dia ou passamos do último dia
                if (slotIndex < offset || slotIndex >= offset + daysInMonth) {
                    // Espaço vazio no calendário
                    Box(modifier = Modifier.weight(1f)) {}
                } else {
                    // Dia efetivo (1..daysInMonth)
                    val day = dayCounter

                    // Monta um Calendar para esse dia
                    val cal = Calendar.getInstance().apply {
                        set(Calendar.YEAR, displayYear)
                        set(Calendar.MONTH, displayMonth)
                        set(Calendar.DAY_OF_MONTH, day)
                    }
                    val dateString = calendarToString(cal) // "yyyy-MM-dd"

                    // Ver se essa data já está cadastrada no Firestore
                    val horarioObj = isDateInList(dateString, horariosList)

                    // Box clicável
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                            .aspectRatio(1f) // mantemos quadrado
                            .clickable {
                                if (horarioObj == null) {
                                    // Dia vazio => mostra diálogo para digitar nº voluntários
                                    dateToAdd = dateString
                                    showDialog = true
                                } else {
                                    // Já existe => remover
                                    val idToRemove = horarioObj.id ?: ""
                                    viewModel.removeHorario(idToRemove)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        // Se existe, pintamos diferente
                        if (horarioObj != null) {
                            Text(
                                text = "$day\n(Ativo: ${horarioObj.numeroMaxVoluntarios})",
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

    // Se showDialog == true, exibimos o AlertDialog
    if (showDialog) {
        DigitarVoluntariosDialog(
            onDismiss = { showDialog = false },
            onConfirm = { numero ->
                // Quando o usuário confirmar, chamamos o ViewModel
                viewModel.addHorario(dateToAdd, numero)
                // Fechamos o diálogo
                showDialog = false
            }
        )
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


// --------------------------------------------------------------------
// DIALOG COM TEXTFIELD PARA DIGITAR O NÚMERO DE VOLUNTÁRIOS
// --------------------------------------------------------------------
@Composable
fun DigitarVoluntariosDialog(
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var inputText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                // Converte o input em Int (ou 0 se inválido)
                val numero = inputText.toIntOrNull() ?: 0
                onConfirm(numero)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        },
        title = { Text("Digite o número máximo de voluntários") },
        text = {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text("Nº Máximo") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    )
}
