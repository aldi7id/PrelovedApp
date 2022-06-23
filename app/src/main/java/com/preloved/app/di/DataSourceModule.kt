package com.preloved.app.di

import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.network.datasource.UserDataSourcelmpl
import com.preloved.app.data.local.datasource.LocalDataSourceImpl
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.data.network.datasource.UserDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataSourceModule = module {
    single { DatastoreManager(androidContext()) }
    single<LocalDataSource> { LocalDataSourceImpl(get()) }
    single<UserDataSource> { UserDataSourcelmpl(get()) }
}