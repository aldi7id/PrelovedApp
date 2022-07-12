package com.preloved.app.di

import com.preloved.app.ui.fragment.homepage.account.AccountViewModel
import com.preloved.app.ui.fragment.homepage.account.password.EditPasswordViewModel
import com.preloved.app.ui.fragment.homepage.home.category.all.CategoryAllViewModel
import com.preloved.app.ui.fragment.homepage.home.category.computer.CategoryComputerViewModel
import com.preloved.app.ui.fragment.homepage.home.category.electronic.CategoryElectronicViewModel
import com.preloved.app.ui.fragment.homepage.home.category.food.CategoryFoodViewModel
import com.preloved.app.ui.fragment.homepage.notification.NotificationViewModel
import com.preloved.app.ui.fragment.homepage.sale.SaleViewModel
import com.preloved.app.ui.fragment.homepage.sell.SellViewModel
import com.preloved.app.ui.fragment.homepage.sell.edit.EditProductViewModel
import com.preloved.app.ui.fragment.homepage.sell.preview.PreviewProductViewModel
import com.preloved.app.ui.fragment.homepage.home.search.SearchProductViewModel
import com.preloved.app.ui.fragment.login.LoginViewModel
import com.preloved.app.ui.fragment.splash.SplashViewModel
import com.preloved.app.ui.fragment.register.RegisterViewModel
import com.preloved.app.ui.profile.edit.EditProfileViewModel
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
    viewModelOf(::EditProfileViewModel)
    viewModelOf(::SellViewModel)
    viewModelOf(::PreviewProductViewModel)
    viewModelOf(::SaleViewModel)
    viewModelOf(::NotificationViewModel)
    viewModelOf(::EditProductViewModel)
    viewModelOf(::EditPasswordViewModel)
}