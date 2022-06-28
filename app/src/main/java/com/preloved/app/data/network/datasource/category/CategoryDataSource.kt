package com.preloved.app.data.network.datasource.category

import com.preloved.app.data.network.model.request.auth.LoginRequest

interface CategoryDataSource {

    suspend fun getCategoryAll(): LoginRequest
    suspend fun getCategoryFood(foodId: Int): LoginRequest
    suspend fun getCategoryFashion(fashionId : Int) : LoginRequest
    suspend fun getCategoryHealthty(healthtyId : Int) : LoginRequest
    suspend fun getCategorySport(sportId : Int) : LoginRequest
    suspend fun getCategoryHobi(hobiId : Int) : LoginRequest
    suspend fun getCategoryElectronic(electronicId : Int) : LoginRequest
    suspend fun getCategoryVehicle(vehicleId : Int) : LoginRequest
    suspend fun getCategorySale(saleId : Int) : LoginRequest
    suspend fun getCategorySell(sellId : Int) : LoginRequest
    suspend fun getCategoryAccessories(accessoriesId : Int) : LoginRequest

}