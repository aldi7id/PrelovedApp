package com.preloved.app.di

import com.preloved.app.ui.login.LoginRepository
import com.preloved.app.ui.register.RegisterRepository
import com.preloved.app.ui.splash.SplashRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::SplashRepository)
    singleOf(::LoginRepository)
    singleOf(::RegisterRepository)
}