package com.example.lojasocial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.lojasocial.data.repository.AuthRepository
import com.example.lojasocial.ui.theme.LojaSocialTheme
import com.example.lojasocial.ui.theme.admin.AceitarUsersScreen
import com.example.lojasocial.ui.theme.admin.ListFuncionamentoView
import com.example.lojasocial.ui.theme.admin.ListSolicitacaoPresencaView
import com.example.lojasocial.ui.theme.beneficiario.AddAgregadoFamiliarView
import com.example.lojasocial.ui.theme.beneficiario.AddBeneficiarioView
import com.example.lojasocial.ui.theme.beneficiario.EditBeneficiarioView
import com.example.lojasocial.ui.theme.beneficiario.ListAgregadoFamiliarView
import com.example.lojasocial.ui.theme.beneficiario.ListBeneficiarioView
import com.example.lojasocial.ui.theme.beneficiario.ListVisitasView
import com.example.lojasocial.ui.theme.diasfuncionamento.ListHorariosFuncionamentoView
import com.example.lojasocial.ui.theme.diasfuncionamento.ListPessoaisSolicitacoesPresencaView
import com.example.lojasocial.ui.theme.home.HomeJuntaView
import com.example.lojasocial.ui.theme.home.HomeView
import com.example.lojasocial.ui.theme.home.HomeViewVoluntario
import com.example.lojasocial.ui.theme.home.HorariosFuncionamentoView
import com.example.lojasocial.ui.theme.login.LoginView
import com.example.lojasocial.ui.theme.pedidos.AddPedidoView
import com.example.lojasocial.ui.theme.pedidos.EditPedidoView
import com.example.lojasocial.ui.theme.pedidos.ListPedidoView
import com.example.lojasocial.ui.theme.register.RegisterView
import com.example.lojasocial.ui.theme.relatorios.EstatisticasScreen
import com.example.lojasocial.ui.theme.transacoes.AddTransactionView
import com.example.lojasocial.ui.theme.transacoes.ListTransactionView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            LojaSocialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = "login"
                    )
                    {

                        composable("login") {
                            LoginView(navController,

                                onLoginSuccess = { role ->
                                    when (role) {
                                        "voluntário" -> navController.navigate("homeVoluntario")
                                        "admin" -> navController.navigate("home")
                                        "membro da junta" -> navController.navigate("homeJunta")
                                        "" -> navController.navigate("login")
                                        else -> navController.navigate("login")
                                    }
                                }
                            )
                        }

                        composable("register"){
                            RegisterView(navController, onRegisterSuccess = { navController.navigate("login") })
                        }

                        composable("home") {
                            HomeView(navController)
                        }

                        composable("homeVoluntario") {
                            HomeViewVoluntario(navController)
                        }

                        composable("homeJunta") {
                            HomeJuntaView(navController)
                        }


                        //Transacoes
                        composable("listTransacao"){
                            ListTransactionView(navController)
                        }

                        composable("addTransacao"){
                            AddTransactionView(navController)
                        }


                        //Beneficiarios
                        composable("listBeneficiario"){
                            ListBeneficiarioView(navController)
                        }

                        composable("addBeneficiario"){
                            AddBeneficiarioView(navController)
                        }

                        composable(
                            route = "listAgregadoFamiliar/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        )
                        { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: ""
                            ListAgregadoFamiliarView(navController, id)
                        }

                        composable(
                            route = "listVisitas/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        )
                        { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: ""
                            ListVisitasView(navController, id)
                        }

                        composable(
                            route = "addAgregadoFamiliar/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        )
                        { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: ""
                            AddAgregadoFamiliarView(navController, id)
                        }

                        composable("listPedido"){
                            ListPedidoView(navController)
                        }

                        composable(
                            route = "addPedido/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        )
                        { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: ""
                            AddPedidoView(navController, id)
                        }

                        composable(
                            route = "editPedido/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        )
                        { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: ""
                            EditPedidoView(navController, id)
                        }

                        composable(
                            route = "editBeneficiario/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        )
                        { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: ""
                            EditBeneficiarioView(navController, id)
                        }

                        //Horarios Funcionamento
                        //voluntario
                        composable("listHorarioFuncionamento"){
                            ListHorariosFuncionamentoView(navController)
                        }

                        //admin
                        composable("listFuncionamento"){
                            ListFuncionamentoView(navController)
                        }

                        composable(
                            route = "listSolicitacaoPresenca/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        )
                        { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: ""
                            ListSolicitacaoPresencaView(navController, id)
                        }

                        composable(
                            route = "listPessoaisSolicitacaoPresenca/{id}",
                            arguments = listOf(navArgument("id") { type = NavType.StringType })
                        )
                        { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id") ?: ""
                            ListPessoaisSolicitacoesPresencaView(navController, id)
                        }

                        composable("addHorariosFuncionamento"){
                            HorariosFuncionamentoView(navController)
                        }

                        //Utilizadores Pendentes
                        composable("aceitarUser"){
                            AceitarUsersScreen(navController)
                        }

                        //Estatísticas
                        composable("estatisticas"){
                            EstatisticasScreen(navController)
                        }

                    }
                }
            }

            LaunchedEffect(Unit) {
                AuthRepository.getUserRole(
                    onSuccess = { role ->
                        when (role) {
                            "voluntário" -> navController.navigate("homeVoluntario")
                            "admin" -> navController.navigate("home")
                            "membro da junta" -> navController.navigate("homeJunta")
                            "" -> navController.navigate("login")
                            else -> navController.navigate("login")
                        }
                    },
                    onFailure = { errorMessage ->
                        navController.navigate("login")
                    }
                )
            }


        }
    }
}