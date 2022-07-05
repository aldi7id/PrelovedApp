package com.preloved.app.data.network.datasource.category

import com.preloved.app.data.network.model.response.category.CategoryResponse
import com.preloved.app.data.network.model.response.category.detail.CategoryDetailResponse
import com.preloved.app.data.network.service.CategoryService

class CategoryDataSourcelmpl(private val categoryService: CategoryService) : CategoryDataSource {
    override suspend fun getCategoryAll(): CategoryResponse {
        return categoryService.getCategoryAll()
    }

    override suspend fun getCategoryFood(foodId: Int): CategoryDetailResponse {
        return categoryService.getCategoryByID(foodId)
    }

    override suspend fun getCategoryFashion(fashionId: Int): CategoryDetailResponse {
        return categoryService.getCategoryByID(fashionId)
    }

    override suspend fun getCategoryHealthty(healthtyId: Int): CategoryDetailResponse {
        return categoryService.getCategoryByID(healthtyId)
    }

    override suspend fun getCategorySport(sportId: Int): CategoryDetailResponse {
        return categoryService.getCategoryByID(sportId)
    }

    override suspend fun getCategoryHobi(hobiId: Int): CategoryDetailResponse {
        return categoryService.getCategoryByID(hobiId)
    }

    override suspend fun getCategoryElectronic(electronicId: Int): CategoryDetailResponse {
        return categoryService.getCategoryByID(electronicId)
    }

    override suspend fun getCategoryVehicle(vehicleId: Int): CategoryDetailResponse {
        return categoryService.getCategoryByID(vehicleId)
    }

    override suspend fun getCategorySale(saleId: Int): CategoryDetailResponse {
        return categoryService.getCategoryByID(saleId)
    }

    override suspend fun getCategorySell(sellId: Int): CategoryDetailResponse {
        return categoryService.getCategoryByID(sellId)
    }

    override suspend fun getCategoryAccessories(accessoriesId: Int): CategoryDetailResponse {
        return categoryService.getCategoryByID(accessoriesId)
    }
}