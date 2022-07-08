package com.preloved.app.di

import com.preloved.app.ui.fragment.homepage.account.AccountViewModel
import com.preloved.app.ui.fragment.homepage.home.category.all.CategoryAllViewModel
import com.preloved.app.ui.fragment.homepage.home.category.computer.CategoryComputerViewModel
import com.preloved.app.ui.fragment.homepage.home.category.electronic.CategoryElectronicViewModel
import com.preloved.app.ui.fragment.homepage.home.category.food.CategoryFoodViewModel
import com.preloved.app.ui.fragment.homepage.home.search.SearchProductViewModel
import com.preloved.app.ui.fragment.login.LoginViewModel
import com.preloved.app.ui.fragment.splash.SplashViewModel
import com.preloved.app.ui.fragment.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::SearchProductViewModel)
    viewModelOf(::CategoryAllViewModel)
    viewModelOf(::CategoryFoodViewModel)
    viewModelOf(::CategoryElectronicViewModel)
    viewModelOf(::CategoryComputerViewModel)
    viewModelOf(::AccountViewModel)
}