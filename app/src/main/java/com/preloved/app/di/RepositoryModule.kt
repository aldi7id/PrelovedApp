package com.preloved.app.di

import com.preloved.app.ui.profile.edit.EditProfileRepository
import com.preloved.app.ui.register.RegisterRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::RegisterRepository)
    singleOf(::EditProfileRepository)
}