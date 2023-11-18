package com.sryang.splash.di.splash_di

import com.sryang.splash.usecase.SplashUseCase
import com.sryang.torang_repository.api.ApiLogin
import com.sryang.torang_repository.data.dao.LoggedInUserDao
import com.sryang.torang_repository.session.SessionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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
                sessionService.getToken()?.let {
                    return apiLogin.sessionCheck(it)
                }
                return false
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