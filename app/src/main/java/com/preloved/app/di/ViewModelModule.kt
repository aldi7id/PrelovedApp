package com.preloved.app.di

import com.preloved.app.ui.profile.ProfileViewModel
import com.preloved.app.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::ProfileViewModel)
}