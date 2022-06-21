package com.preloved.app.di

//import com.preloved.app.data.network.datasource.UserDataSource
//import com.preloved.app.data.network.datasource.UserDataSourcelmpl
import com.preloved.app.data.local.datasource.LocalDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataSourceModule = module {
//    single<UserDataSource> { UserDataSourcelmpl(get()) }
    singleOf(::LocalDataSourceImpl)
}