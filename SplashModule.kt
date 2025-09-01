package com.sryang.splash.di.splash_di

import com.sryang.splash.usecase.SplashUseCase
import com.sarang.torang.api.ApiLogin
import com.sarang.torang.api.handle
import com.sarang.torang.core.database.dao.LoggedInUserDao
import com.sarang.torang.session.SessionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.net.UnknownHostException

@InstallIn(SingletonComponent::class)
@Module
class SplashModule {
    @Provides
    fun provideSplashService(
        sessionService: SessionService,
        apiLogin: ApiLogin,
        loggedInUserDao: LoggedInUserDao
    ): SplashUseCase {
        return object : SplashUseCase {
            override suspend fun checkSession(): Boolean {
                try {
                    sessionService.getToken()?.let {
                        return apiLogin.sessionCheck(it)
                    }
                    return false
                } catch (e: UnknownHostException) {
                    throw Exception(e.handle())
                }
            }

            override suspend fun logout() {
                sessionService.removeToken()
                loggedInUserDao.clear()
            }

            override suspend fun isLogin(): Boolean {
                return sessionService.getToken() != null
            }


        }
    }
}