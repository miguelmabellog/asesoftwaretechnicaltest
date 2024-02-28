package com.miguel.asesoftwaretechnicaltest.usecase

import com.miguel.asesoftwaretechnicaltest.data.Ktorapiphoto
import com.miguel.asesoftwaretechnicaltest.repository.PhotoDomain
import com.miguel.asesoftwaretechnicaltest.repository.PhotoRepository

class FetchPhotosUseCase(private val photoRepository: PhotoRepository) : UseCase<Unit, List<PhotoDomain>>() {
    companion object {
        val fetchPhotosUseCase = FetchPhotosUseCase(PhotoRepository.photoRepository)
    }

    override suspend fun execute(params: Unit): Result<List<PhotoDomain>> {
        return try {
            val result = photoRepository.getPhotos()
            Success(result)
        } catch (e: Exception) {
            Error("Error al ejecutar el caso de uso: ${e.message}")
        }
    }
}