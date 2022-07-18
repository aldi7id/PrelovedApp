package com.preloved.app.di

import com.preloved.app.ui.fragment.homepage.account.AccountViewModel
import com.preloved.app.ui.fragment.homepage.account.password.EditPasswordViewModel
import com.preloved.app.ui.fragment.homepage.home.category.accessories.CategoryAccessoriesViewModel
import com.preloved.app.ui.fragment.homepage.home.category.all.CategoryAllViewModel
import com.preloved.app.ui.fragment.homepage.home.category.automotive.CategoryAutomotiveViewModel
import com.preloved.app.ui.fragment.homepage.home.category.babyfashion.CategoryBabyFashionViewModel
import com.preloved.app.ui.fragment.homepage.home.category.beauty.CategoryBeautyViewModel
import com.preloved.app.ui.fragment.homepage.home.category.bookandpen.CategoryBookAndPenViewModel
import com.preloved.app.ui.fragment.homepage.home.category.computer.CategoryComputerViewModel
import com.preloved.app.ui.fragment.homepage.home.category.electronic.CategoryElectronicViewModel
import com.preloved.app.ui.fragment.homepage.home.category.fashionmuslim.CategoryFashionMuslimViewModel
import com.preloved.app.ui.fragment.homepage.home.category.food.CategoryFoodViewModel
import com.preloved.app.ui.fragment.homepage.notification.NotificationViewModel
import com.preloved.app.ui.fragment.homepage.sale.SaleViewModel
import com.preloved.app.ui.fragment.homepage.sell.SellViewModel
import com.preloved.app.ui.fragment.homepage.sell.edit.EditProductViewModel
import com.preloved.app.ui.fragment.homepage.sell.preview.PreviewProductViewModel
import com.preloved.app.ui.fragment.homepage.home.category.healthy.CategoryHealthyViewModel
import com.preloved.app.ui.fragment.homepage.home.category.hobby.CategoryHobbyViewModel
import com.preloved.app.ui.fragment.homepage.home.category.homesupplies.CategoryHomeSuppliesViewModel
import com.preloved.app.ui.fragment.homepage.home.category.man.bag.CategoryBagMenViewModel
import com.preloved.app.ui.fragment.homepage.home.category.man.cloth.CategoryClothMenViewModel
import com.preloved.app.ui.fragment.homepage.home.category.man.shoes.CategoryShoesMenViewModel
import com.preloved.app.ui.fragment.homepage.home.category.momandbaby.CategoryMomAndBabyViewModel
import com.preloved.app.ui.fragment.homepage.home.category.photographer.CategoryPhotographerViewModel
import com.preloved.app.ui.fragment.homepage.home.category.smartphone.CategorySmartphoneViewModel
import com.preloved.app.ui.fragment.homepage.home.category.souvenir.CategorySouvenirViewModel
import com.preloved.app.ui.fragment.homepage.home.category.voucher.CategoryVoucherViewModel
import com.preloved.app.ui.fragment.homepage.home.category.woman.bag.CategoryBagWomenViewModel
import com.preloved.app.ui.fragment.homepage.home.category.woman.cloth.CategoryClothWomenViewModel
import com.preloved.app.ui.fragment.homepage.home.category.woman.shoes.CategoryShoesWomenViewModel
import com.preloved.app.ui.fragment.homepage.home.category.workout.CategoryWorkoutViewModel
import com.preloved.app.ui.fragment.homepage.home.detail.DetailProductViewModel
import com.preloved.app.ui.fragment.homepage.home.search.SearchProductViewModel
import com.preloved.app.ui.fragment.login.LoginViewModel
import com.preloved.app.ui.fragment.splash.SplashViewModel
import com.preloved.app.ui.fragment.register.RegisterViewModel
import com.preloved.app.ui.fragment.homepage.account.edit.EditProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SplashViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::SearchProductViewModel)
    viewModelOf(::CategoryAllViewModel)
    viewModelOf(::CategoryComputerViewModel)
    viewModelOf(::CategoryElectronicViewModel)
    viewModelOf(::CategoryClothMenViewModel)
    viewModelOf(::CategoryShoesMenViewModel)
    viewModelOf(::CategoryBagMenViewModel)
    viewModelOf(::CategoryAccessoriesViewModel)
    viewModelOf(::CategoryHealthyViewModel)
    viewModelOf(::CategoryHobbyViewModel)
    viewModelOf(::CategoryFoodViewModel)
    viewModelOf(::CategoryBeautyViewModel)
    viewModelOf(::CategoryHomeSuppliesViewModel)
    viewModelOf(::CategoryClothWomenViewModel)
    viewModelOf(::CategoryFashionMuslimViewModel)
    viewModelOf(::CategoryBabyFashionViewModel)
    viewModelOf(::CategoryMomAndBabyViewModel)
    viewModelOf(::CategoryShoesWomenViewModel)
    viewModelOf(::CategoryBagWomenViewModel)
    viewModelOf(::CategoryAutomotiveViewModel)
    viewModelOf(::CategoryWorkoutViewModel)
    viewModelOf(::CategoryBookAndPenViewModel)
    viewModelOf(::CategoryVoucherViewModel)
    viewModelOf(::CategorySouvenirViewModel)
    viewModelOf(::CategoryPhotographerViewModel)
    viewModelOf(::CategorySmartphoneViewModel)
    viewModelOf(::DetailProductViewModel)
    viewModelOf(::AccountViewModel)
    viewModelOf(::EditProfileViewModel)
    viewModelOf(::SellViewModel)
    viewModelOf(::PreviewProductViewModel)
    viewModelOf(::SaleViewModel)
    viewModelOf(::NotificationViewModel)
    viewModelOf(::EditProductViewModel)
    viewModelOf(::EditPasswordViewModel)
}