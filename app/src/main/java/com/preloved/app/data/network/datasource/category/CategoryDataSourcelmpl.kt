package com.preloved.app.data.network.datasource.category

import com.preloved.app.data.network.model.request.auth.LoginRequest
import com.preloved.app.data.network.service.CategoryService

class CategoryDataSourcelmpl(private val categoryService: CategoryService) : CategoryDataSource {
    override suspend fun getCategoryAll(): LoginRequest {
        return categoryService.getCategoryAll()
    }

    override suspend fun getCategoryFood(foodId: Int): LoginRequest {
        return categoryService.getCategoryByID(foodId)
    }

    override suspend fun getCategoryFashion(fashionId: Int): LoginRequest {
        return categoryService.getCategoryByID(fashionId)
    }

    override suspend fun getCategoryHealthty(healthtyId: Int): LoginRequest {
        return categoryService.getCategoryByID(healthtyId)
    }

    override suspend fun getCategorySport(sportId: Int): LoginRequest {
        return categoryService.getCategoryByID(sportId)
    }

    override suspend fun getCategoryHobi(hobiId: Int): LoginRequest {
        return categoryService.getCategoryByID(hobiId)
    }

    override suspend fun getCategoryElectronic(electronicId: Int): LoginRequest {
        return categoryService.getCategoryByID(electronicId)
    }

    override suspend fun getCategoryVehicle(vehicleId: Int): LoginRequest {
        return categoryService.getCategoryByID(vehicleId)
    }

    override suspend fun getCategorySale(saleId: Int): LoginRequest {
        return categoryService.getCategoryByID(saleId)
    }

    override suspend fun getCategorySell(sellId: Int): LoginRequest {
        return categoryService.getCategoryByID(sellId)
    }

    override suspend fun getCategoryAccessories(accessoriesId: Int): LoginRequest {
        return categoryService.getCategoryByID(accessoriesId)
    }

}