package com.miguel.asesoftwaretechnicaltest.usecase

import android.content.Context
import com.miguel.asesoftwaretechnicaltest.repository.PhotoDomain
import com.miguel.asesoftwaretechnicaltest.repository.PhotoRepository



class GetPhotoByIdUseCase(private val context: Context, private val photoRepository: PhotoRepository) : UseCase<Int, PhotoDomain?>() {
    companion object {
        fun getInstance(context: Context): GetPhotoByIdUseCase {
            val photoRepository = PhotoRepository.getInstance(context)
            return GetPhotoByIdUseCase(context, photoRepository)
        }
    }

    override suspend fun execute(params: Int): Result<PhotoDomain> {
        return try {
            val result=photoRepository.getPhotoById(params)
            Success(result)
        } catch (e: Exception) {
            Error("Error al ejecutar el caso de uso: ${e.message}")
        }
    }
}