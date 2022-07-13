package com.preloved.app.di

import com.preloved.app.ui.fragment.homepage.account.AccountRepository
import com.preloved.app.ui.fragment.homepage.home.category.all.CategoryAllRepository
import com.preloved.app.ui.fragment.homepage.home.category.computer.CategoryComputerRepository
import com.preloved.app.ui.fragment.homepage.home.category.electronic.CategoryElectronicRepository
import com.preloved.app.ui.fragment.homepage.home.category.food.CategoryFoodRepository
import com.preloved.app.ui.fragment.homepage.home.category.smartphone.CategorySmartphoneRepository
import com.preloved.app.ui.fragment.homepage.home.detail.DetailProductRepository
import com.preloved.app.ui.fragment.homepage.home.search.SearchProductRepository
import com.preloved.app.ui.fragment.login.LoginRepository
import com.preloved.app.ui.fragment.register.RegisterRepository
import com.preloved.app.ui.fragment.splash.SplashRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::SplashRepository)
    singleOf(::LoginRepository)
    singleOf(::RegisterRepository)
    singleOf(::SearchProductRepository)
    singleOf(::CategoryAllRepository)
    singleOf(::CategoryFoodRepository)
    singleOf(::CategoryElectronicRepository)
    singleOf(::CategoryComputerRepository)
    singleOf(::CategorySmartphoneRepository)
    singleOf(::DetailProductRepository)
    singleOf(::AccountRepository)
}