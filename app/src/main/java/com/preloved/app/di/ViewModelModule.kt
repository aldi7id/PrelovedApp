package com.preloved.app.di

import com.preloved.app.ui.fragment.homepage.home.category.all.CategoryAllViewModel
import com.preloved.app.ui.fragment.login.LoginViewModel
import com.preloved.app.ui.fragment.splash.SplashViewModel
import com.preloved.app.ui.fragment.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::CategoryAllViewModel)
}