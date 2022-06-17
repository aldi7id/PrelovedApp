package com.preloved.app.di

import com.preloved.app.data.network.datasource.UserDataSource
import com.preloved.app.data.network.datasource.UserDataSourcelmpl
import org.koin.dsl.module

val dataSourceModule = module {
    single<UserDataSource> { UserDataSourcelmpl(get()) }
}