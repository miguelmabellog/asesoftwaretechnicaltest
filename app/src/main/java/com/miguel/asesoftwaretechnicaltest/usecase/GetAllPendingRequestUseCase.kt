package com.miguel.asesoftwaretechnicaltest.usecase

import android.content.Context
import com.miguel.asesoftwaretechnicaltest.repository.PendingRequest
import com.miguel.asesoftwaretechnicaltest.repository.PhotoRepository


class GetAllPendingRequestUseCase(private val context: Context, private val photoRepository: PhotoRepository) : UseCase<Unit, List<PendingRequest>>() {
    companion object {
        fun getInstance(context: Context): GetAllPendingRequestUseCase {
            val photoRepository = PhotoRepository.getInstance(context)
            return GetAllPendingRequestUseCase(context, photoRepository)
        }
    }

    override suspend fun execute(params: Unit): Result<List<PendingRequest>> {
        return try {
            val result = photoRepository.getAllPendingRequest()
            Success(result)
        } catch (e: Exception) {
            Error("Error al ejecutar el caso de uso: ${e.message}")
        }
    }
}