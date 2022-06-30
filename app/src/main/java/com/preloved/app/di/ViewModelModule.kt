package com.preloved.app.di

import com.preloved.app.ui.login.LoginViewModel
import com.preloved.app.ui.splash.SplashViewModel
import com.preloved.app.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
}