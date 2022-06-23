package com.preloved.app.di

import com.preloved.app.ui.product.add.AddProductViewModel
import com.preloved.app.ui.profile.edit.EditProfileViewModel
import com.preloved.app.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::EditProfileViewModel)
    viewModelOf(::AddProductViewModel)
}