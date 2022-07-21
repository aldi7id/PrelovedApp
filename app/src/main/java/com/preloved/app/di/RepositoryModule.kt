package com.preloved.app.di

import com.preloved.app.ui.fragment.homepage.account.AccountRepository
import com.preloved.app.ui.fragment.homepage.home.bid.PopUpBidRepository
import com.preloved.app.ui.fragment.homepage.home.category.accessories.CategoryAccessoriesRepository
import com.preloved.app.ui.fragment.homepage.home.category.all.CategoryAllRepository
import com.preloved.app.ui.fragment.homepage.home.category.automotive.CategoryAutomotiveRepository
import com.preloved.app.ui.fragment.homepage.home.category.babyfashion.CategoryBabyFashionRepository
import com.preloved.app.ui.fragment.homepage.home.category.beauty.CategoryBeautyRepository
import com.preloved.app.ui.fragment.homepage.home.category.bookandpen.CategoryBookAndPenRepository
import com.preloved.app.ui.fragment.homepage.home.category.computer.CategoryComputerRepository
import com.preloved.app.ui.fragment.homepage.home.category.electronic.CategoryElectronicRepository
import com.preloved.app.ui.fragment.homepage.home.category.fashionmuslim.CategoryFashionMuslimRepository
import com.preloved.app.ui.fragment.homepage.home.category.food.CategoryFoodRepository
import com.preloved.app.ui.fragment.homepage.home.category.healthy.CategoryHealthyRepository
import com.preloved.app.ui.fragment.homepage.home.category.hobby.CategoryHobbyRepository
import com.preloved.app.ui.fragment.homepage.home.category.homesupplies.CategoryHomeSuppliesRepository
import com.preloved.app.ui.fragment.homepage.home.category.man.bag.CategoryBagMenRepository
import com.preloved.app.ui.fragment.homepage.home.category.man.cloth.CategoryClothMenRepository
import com.preloved.app.ui.fragment.homepage.home.category.man.shoes.CategoryShoesMenRepository
import com.preloved.app.ui.fragment.homepage.home.category.momandbaby.CategoryMomAndBabyRepository
import com.preloved.app.ui.fragment.homepage.home.category.photographer.CategoryPhotographerRepository
import com.preloved.app.ui.fragment.homepage.home.category.smartphone.CategorySmartphoneRepository
import com.preloved.app.ui.fragment.homepage.home.category.souvenir.CategorySouvenirRepository
import com.preloved.app.ui.fragment.homepage.home.category.voucher.CategoryVoucherRepository
import com.preloved.app.ui.fragment.homepage.home.category.woman.bag.CategoryBagWomenRepository
import com.preloved.app.ui.fragment.homepage.home.category.woman.cloth.CategoryClothWomenRepository
import com.preloved.app.ui.fragment.homepage.home.category.woman.shoes.CategoryShoesWomenRepository
import com.preloved.app.ui.fragment.homepage.home.category.workout.CategoryWorkoutRepository
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
    singleOf(::CategoryElectronicRepository)
    singleOf(::CategoryComputerRepository)
    singleOf(::CategorySmartphoneRepository)
    singleOf(::CategoryClothMenRepository)
    singleOf(::CategoryShoesMenRepository)
    singleOf(::CategoryBagMenRepository)
    singleOf(::CategoryAccessoriesRepository)
    singleOf(::CategoryHealthyRepository)
    singleOf(::CategoryHobbyRepository)
    singleOf(::CategoryFoodRepository)
    singleOf(::CategoryBeautyRepository)
    singleOf(::CategoryHomeSuppliesRepository)
    singleOf(::CategoryClothWomenRepository)
    singleOf(::CategoryFashionMuslimRepository)
    singleOf(::CategoryBabyFashionRepository)
    singleOf(::CategoryMomAndBabyRepository)
    singleOf(::CategoryShoesWomenRepository)
    singleOf(::CategoryBagWomenRepository)
    singleOf(::CategoryAutomotiveRepository)
    singleOf(::CategoryWorkoutRepository)
    singleOf(::CategoryBookAndPenRepository)
    singleOf(::CategoryVoucherRepository)
    singleOf(::CategorySouvenirRepository)
    singleOf(::CategoryPhotographerRepository)
    singleOf(::DetailProductRepository)
    singleOf(::PopUpBidRepository)
    singleOf(::AccountRepository)
}