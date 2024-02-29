package com.miguel.asesoftwaretechnicaltest.usecase

import android.content.Context
import com.miguel.asesoftwaretechnicaltest.repository.PhotoDomain
import com.miguel.asesoftwaretechnicaltest.repository.PhotoRepository


class DeletePhotoUseCase(private val context: Context, private val photoRepository: PhotoRepository) : UseCase<Int, Unit>() {
    companion object {
        fun getInstance(context: Context): DeletePhotoUseCase {
            val photoRepository = PhotoRepository.getInstance(context)
            return DeletePhotoUseCase(context, photoRepository)
        }
    }

    override suspend fun execute(params: Int): Result<Unit> {
        return try {
            val result=photoRepository.deletePhoto(params)
            Success(result)
        } catch (e: Exception) {
            Error("Error al ejecutar el caso de uso: ${e.message}")
        }
    }
}