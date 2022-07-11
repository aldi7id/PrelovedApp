package com.preloved.app.di

import com.preloved.app.ui.fragment.homepage.account.AccountRepository
import com.preloved.app.ui.fragment.homepage.home.category.all.CategoryAllRepository
import com.preloved.app.ui.fragment.homepage.home.category.computer.CategoryComputerRepository
import com.preloved.app.ui.fragment.homepage.home.category.electronic.CategoryElectronicRepository
import com.preloved.app.ui.fragment.homepage.home.category.food.CategoryFoodRepository
import com.preloved.app.ui.fragment.homepage.home.category.food.CategoryFoodViewModel
import com.preloved.app.ui.fragment.homepage.notification.NotificationRepository
import com.preloved.app.ui.fragment.homepage.sale.SaleRepository
import com.preloved.app.ui.fragment.homepage.sell.SellRepository
import com.preloved.app.ui.fragment.homepage.sell.edit.EditProductRepository
import com.preloved.app.ui.fragment.homepage.sell.preview.PreviewProductRepository
import com.preloved.app.ui.fragment.login.LoginRepository
import com.preloved.app.ui.fragment.register.RegisterRepository
import com.preloved.app.ui.fragment.splash.SplashRepository
import com.preloved.app.ui.profile.edit.EditProfileRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::SplashRepository)
    singleOf(::LoginRepository)
    singleOf(::RegisterRepository)
    singleOf(::EditProfileRepository)
    singleOf(::CategoryAllRepository)
    singleOf(::CategoryFoodRepository)
    singleOf(::CategoryElectronicRepository)
    singleOf(::CategoryComputerRepository)
    singleOf(::AccountRepository)
    singleOf(::SellRepository)
    singleOf(::PreviewProductRepository)
    singleOf(::SaleRepository)
    singleOf(::NotificationRepository)
    singleOf(::EditProductRepository)
}