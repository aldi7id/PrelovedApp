package com.preloved.app.di

import com.preloved.app.data.local.datasource.LocalDataSource
import com.preloved.app.data.network.datasource.user.UserDataSourcelmpl
import com.preloved.app.data.local.datasource.LocalDataSourceImpl
import com.preloved.app.data.local.datastore.DatastoreManager
import com.preloved.app.data.network.datasource.category.CategoryDataSource
import com.preloved.app.data.network.datasource.category.CategoryDataSourcelmpl
import com.preloved.app.data.network.datasource.user.UserDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataSourceModule = module {
    single { DatastoreManager(androidContext()) }
    single<LocalDataSource> { LocalDataSourceImpl(get()) }
    single<UserDataSource> { UserDataSourcelmpl(get()) }
    single<CategoryDataSource> {CategoryDataSourcelmpl(get())}
}