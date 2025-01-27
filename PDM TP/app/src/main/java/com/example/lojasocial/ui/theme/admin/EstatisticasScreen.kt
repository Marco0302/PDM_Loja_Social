package com.example.lojasocial.ui.theme.relatorios

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Environment
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.lojasocial.data.repository.EstatisticasRepository
import com.example.lojasocial.ui.theme.bars.TopBar
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class EstatisticasViewModel : ViewModel() {

    var nacionalidadeMap by mutableStateOf<Map<String, Int>>(emptyMap())
        private set

    var visitasAnoMap by mutableStateOf<Map<String, Int>>(emptyMap())
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        // Quando o ViewModel for criado, buscar as duas estatísticas
        carregarNacionalidade()
        carregarVisitasPorAno()
    }

    private fun carregarNacionalidade() {
        EstatisticasRepository.getEstatisticasNacionalidade(
            onSuccess = { map ->
                nacionalidadeMap = map
            },
            onFailure = { e ->
                errorMessage = e.message
            }
        )
    }

    private fun carregarVisitasPorAno() {
        EstatisticasRepository.getVisitasPorAno(
            onSuccess = { map ->
                visitasAnoMap = map
            },
            onFailure = { e ->
                errorMessage = e.message
            }
        )
    }
}

/**
 * Tela principal que exibe dois gráficos (PieChart e BarChart) usando MPAndroidChart
 * via AndroidView, tudo em Compose.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstatisticasScreen(
    navController: NavController,
    viewModel: EstatisticasViewModel = viewModel(),
) {
    val nacionalidadeMap = viewModel.nacionalidadeMap
    val visitasAnoMap = viewModel.visitasAnoMap
    val errorMessage = viewModel.errorMessage

    Scaffold(
        topBar = {
            TopBar(title = "Dados Estatísticos", navController = navController)
        }
    ) { innerPadding ->
        // Layout principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Mensagem de erro, se houver
            errorMessage?.let {
                Text(
                    text = "Erro: $it",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = { exportPdf() },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Exportar")
            }

            // PieChart (Nacionalidades)
            Text(
                text = "Visitas por Nacionalidade",
                style = MaterialTheme.typography.titleMedium
            )
            // Aumentar a altura do gráfico
            MPAndroidPieChart(
                dataMap = nacionalidadeMap,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp) // Ajuste conforme necessário
            )

            // BarChart (Visitas por Ano)
            Text(
                text = "Visitas por Ano",
                style = MaterialTheme.typography.titleMedium
            )
            MPAndroidBarChart(
                dataMap = visitasAnoMap,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp) // Ajuste para ficar maior
            )


        }
    }
}

/**
 * Composable para desenhar um PieChart do MPAndroidChart
 * usando AndroidView. Recebe um Map<String, Int>, ex.: "Portuguesa" -> 10
 */
@Composable
fun MPAndroidPieChart(
    dataMap: Map<String, Int>,
    modifier: Modifier = Modifier
) {
    // Converter Map -> List<PieEntry>
    val entries = dataMap.map { (label, value) ->
        PieEntry(value.toFloat(), label)
    }

    // Use AndroidView para integrar a View do MPAndroidChart
    AndroidView(
        modifier = modifier,
        factory = { context: Context ->
            val pieChart = PieChart(context)

            // Configurações iniciais
            pieChart.description.isEnabled = false
            pieChart.isDrawHoleEnabled = false  // se não quiser donut
            pieChart.setEntryLabelTextSize(14f)

            pieChart
        },
        update = { pieChart ->
            // Atualiza os dados sempre que dataMap mudar
            val dataSet = PieDataSet(entries, "Nacionalidades")
            // Usando algumas cores do MPAndroidChart
            dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()

            val pieData = PieData(dataSet)
            pieData.setValueTextSize(12f)

            pieChart.data = pieData
            pieChart.animateXY(1000, 1000)
            pieChart.invalidate()
        }
    )
}

/**
 * Composable para desenhar um BarChart do MPAndroidChart
 * usando AndroidView. Recebe um Map<String, Int>, ex.: "2025" -> 7
 */
@Composable
fun MPAndroidBarChart(
    dataMap: Map<String, Int>,
    modifier: Modifier = Modifier
) {
    // Precisamos de "labels" e de "entries" (BarEntry).
    // index -> float X, value -> float Y
    val sortedKeys = dataMap.keys.sorted()
    val entries = sortedKeys.mapIndexed { index, year ->
        val valor = dataMap[year]?.toFloat() ?: 0f
        BarEntry(index.toFloat(), valor, year)
    }

    AndroidView(
        modifier = modifier,
        factory = { context: Context ->
            val barChart = BarChart(context)

            // Configurações iniciais
            barChart.description.isEnabled = false
            barChart.setFitBars(true)
            barChart.axisLeft.axisMinimum = 0f
            barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            barChart.xAxis.granularity = 1f
            barChart.xAxis.setDrawGridLines(false)

            // Desativa legenda se quiser
            barChart.legend.isEnabled = true

            barChart
        },
        update = { chart ->
            val dataSet = BarDataSet(entries, "Visitas por Ano")
            dataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()

            val barData = BarData(dataSet)
            barData.barWidth = 0.5f

            chart.data = barData

            // Eixo X: exibir as strings (anos) no lugar do index
            chart.xAxis.valueFormatter = IndexAxisValueFormatter(sortedKeys)

            // Animação
            chart.animateXY(1000, 1000)

            // Redesenhar
            chart.invalidate()
        }
    )
}

fun exportPdf() {
    // Criar uma instância de PdfDocument
    val pdfDocument = PdfDocument()

    // Configurar uma página no PDF
    val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
    val page = pdfDocument.startPage(pageInfo)

    // Obter o Canvas da página
    val canvas = page.canvas

    // Adicionar conteúdo no Canvas
    val text = """
        Portugal - 2
        Espanha - 3
        França - 4
    """.trimIndent()
    canvas.drawText(text, 10f, 50f, android.graphics.Paint())

    // Finalizar a página
    pdfDocument.finishPage(page)

    // Criar um diretório onde o PDF será salvo, caso não exista
    val docsFolder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Relatorios")

    // Verificar se o diretório existe, caso contrário, criá-lo
    if (!docsFolder.exists()) {
        docsFolder.mkdirs()  // Cria a pasta se não existir
    }

    // Definir o caminho completo para o arquivo PDF
    val outputDirectory = File(docsFolder, "Relatorio.pdf")

    try {
        // Escrever o conteúdo do PDF no arquivo
        pdfDocument.writeTo(FileOutputStream(outputDirectory))
        println("PDF exportado com sucesso: ${outputDirectory.absolutePath}")
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        // Fechar o documento PDF
        pdfDocument.close()
    }
}



