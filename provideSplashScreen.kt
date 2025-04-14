package com.sarang.torang.di.splash_di

import androidx.compose.runtime.Composable
import com.sarang.torang.RootNavController
import com.sryang.splash.compose.SplashScreen
import com.sryang.splash.uistate.LoginState

fun provideSplashScreen(navHostController: RootNavController) : @Composable () -> Unit = {
    SplashScreen(onLoginState = {
        when (it) {
            LoginState.SESSION_EXPIRED -> {
                navHostController.singleTopLogin()
            }

            LoginState.LOGIN -> {
                navHostController.singleTopMain()
            }

            LoginState.LOGOUT -> {
                navHostController.singleTopLogin()
            }

            else -> {

            }
        }
    })
}