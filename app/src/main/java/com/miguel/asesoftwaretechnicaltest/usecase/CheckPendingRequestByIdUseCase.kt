package com.miguel.asesoftwaretechnicaltest.usecase

import android.content.Context
import com.miguel.asesoftwaretechnicaltest.repository.PendingRequest
import com.miguel.asesoftwaretechnicaltest.repository.PhotoRepository



class CheckPendingRequestByIdUseCase(private val context: Context, private val photoRepository: PhotoRepository) : UseCase<Int, PendingRequest?>() {
    companion object {
        fun getInstance(context: Context): CheckPendingRequestByIdUseCase {
            val photoRepository = PhotoRepository.getInstance(context)
            return CheckPendingRequestByIdUseCase(context, photoRepository)
        }
    }

    override suspend fun execute(params: Int): Result<PendingRequest?> {
        return try {
            val result=photoRepository.getPendingDeleteById(params)
            Success(result)
        } catch (e: Exception) {
            Error("Error al ejecutar el caso de uso: ${e.message}")
        }
    }
}