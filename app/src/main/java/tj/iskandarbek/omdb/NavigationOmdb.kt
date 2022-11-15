package tj.iskandarbek.omdb

import androidx.compose.animation.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import tj.iskandarbek.omdb.presentation.DetailScreen
import tj.iskandarbek.omdb.presentation.SearchScreen


sealed class Navigationomdb(val route: String){

    object SearchScreen: Screen("myrequest_screen")
    object DetailScreen: Screen("detail_screen/{caseid}"){
        fun withArgs(caseid: String): String = "detail_screen/$caseid"
    }



    fun withArgs(vararg args: String):String{
        return buildString {
            append(route)
            args.forEach { arg->
                append("/$arg")
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.omdb(
    navController: NavHostController,
    restartActivity: () -> Unit,
) {

    navigation(
        route = Screen.MainScreen.route,
        startDestination = Navigationomdb.SearchScreen.route,
    ) {


        composable(route = Navigationomdb.SearchScreen.route)
        {
            SearchScreen(navController = navController)
        }

        composable(route = Navigationomdb.DetailScreen.route)
        {entry->
            val caseid = entry.arguments?.getString("caseid")
            DetailScreen(navController = navController)
        }


    }
}