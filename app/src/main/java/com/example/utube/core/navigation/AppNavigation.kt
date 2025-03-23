package com.example.utube.core.navigation


import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.utube.core.auth.AuthViewModel
import com.example.utube.utubeapp.presentation.homescreen.HomeScreen
import com.example.utube.utubeapp.presentation.homescreen.components.search.SearchScreenRoot
import com.example.utube.utubeapp.presentation.homescreen.components.search.SearchViewModel
import com.example.utube.utubeapp.presentation.log_in.LoginScreen
import com.example.utube.utubeapp.presentation.sign_in.SignUpScreen
import com.example.utube.utubeapp.presentation.videodetail.VideoDetailScreenRoot


@SuppressLint("WrongNavigateRouteType")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyAppNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel(),
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
        builder = {
        composable("login"){
            LoginScreen(modifier,navController,authViewModel)
        }
        composable("signup"){
            SignUpScreen(modifier,navController,authViewModel)
        }
        composable("homescreen"){
            HomeScreen(modifier,navController, authViewModel)
        }
        composable("searchscreen"){
            val viewModel = hiltViewModel<SearchViewModel>()
            SearchScreenRoot(
                navController,
                viewModel,
                onSearchResultClick = {video->
                    Log.d("koi", "selected:  ${video.id.videoId}")
                    val s = video.id.videoId
                    navController.navigate(
                        "videodetailscreen/$s"
                    )
                }
            )
        }
        composable(
            "videodetailscreen/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
            ){
            val id = it.arguments?.getString("id")

            VideoDetailScreenRoot(
                onBackClick = {
                    navController.navigateUp()
                }
            )
        }
    })
}
