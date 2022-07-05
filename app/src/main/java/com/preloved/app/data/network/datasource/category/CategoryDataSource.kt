package com.preloved.app.data.network.datasource.category

import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse

interface CategoryDataSource {

    suspend fun getCategoryAll(): CategoryResponse
    suspend fun getCategoryFood(foodId: Int): CategoryDetailResponse
    suspend fun getCategoryFashion(fashionId : Int) : CategoryDetailResponse
    suspend fun getCategoryHealthty(healthtyId : Int) : CategoryDetailResponse
    suspend fun getCategorySport(sportId : Int) : CategoryDetailResponse
    suspend fun getCategoryHobi(hobiId : Int) : CategoryDetailResponse
    suspend fun getCategoryElectronic(electronicId : Int) : CategoryDetailResponse
    suspend fun getCategoryVehicle(vehicleId : Int) : CategoryDetailResponse
    suspend fun getCategorySale(saleId : Int) : CategoryDetailResponse
    suspend fun getCategorySell(sellId : Int) : CategoryDetailResponse
    suspend fun getCategoryAccessories(accessoriesId : Int) : CategoryDetailResponse

}