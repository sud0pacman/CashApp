import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sudo_pacman.cashapp.ui.navigation.Routes
import com.sudo_pacman.cashapp.ui.screen.add_card_screen.AddCardScreen
import com.sudo_pacman.cashapp.ui.screen.wallet_screen.WalletScreen


@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Home
    ) {
        composable(Routes.Home) { WalletScreen(navController = navController) }
        composable(Routes.AddCard + "/{phoneNumber}") { backStackEntry ->
            AddCardScreen(navController = navController, phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: "+998901234567")
        }
    }
}
