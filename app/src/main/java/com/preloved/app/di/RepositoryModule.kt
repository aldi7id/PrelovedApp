package com.preloved.app.di

import com.preloved.app.ui.login.LoginRepository
import com.preloved.app.ui.register.RegisterRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::LoginRepository)
    singleOf(::RegisterRepository)
}