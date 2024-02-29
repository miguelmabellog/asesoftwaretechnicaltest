package com.miguel.asesoftwaretechnicaltest.usecase

import android.content.Context
import com.miguel.asesoftwaretechnicaltest.data.Ktorapiphoto
import com.miguel.asesoftwaretechnicaltest.repository.PhotoDomain
import com.miguel.asesoftwaretechnicaltest.repository.PhotoRepository

class FetchPhotosUseCase(private val context: Context, private val photoRepository: PhotoRepository) : UseCase<Unit, List<PhotoDomain>>() {
    companion object {
        fun getInstance(context: Context): FetchPhotosUseCase {
            val photoRepository = PhotoRepository.getInstance(context)
            return FetchPhotosUseCase(context, photoRepository)
        }
    }

    override suspend fun execute(params: Unit): Result<List<PhotoDomain>> {
        return try {
            val result = photoRepository.getPhotos()
            // poner savephoto en otro use case
            photoRepository.savePhotosLocally(result)
            Success(result)
        } catch (e: Exception) {
            Error("Error al ejecutar el caso de uso: ${e.message}")
        }
    }
}