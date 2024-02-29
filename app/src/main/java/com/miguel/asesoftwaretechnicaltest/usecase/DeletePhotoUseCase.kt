package com.miguel.asesoftwaretechnicaltest.usecase

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.miguel.asesoftwaretechnicaltest.repository.PhotoDomain
import com.miguel.asesoftwaretechnicaltest.repository.PhotoRepository


class DeletePhotoUseCase(private val context: Context, private val photoRepository: PhotoRepository) : UseCase<Int, Boolean>() {
    companion object {
        fun getInstance(context: Context): DeletePhotoUseCase {
            val photoRepository = PhotoRepository.getInstance(context)
            return DeletePhotoUseCase(context, photoRepository)
        }
    }

    override suspend fun execute(params: Int): Result<Boolean> {
        return try {
            if(isInternetAvailable()) {
                photoRepository.deletePhoto(params)
                Success(false)
            }else{
                photoRepository.savePendingDeleteByID(params)
                Success(true)
            }
        } catch (e: Exception) {
            Error("Error al ejecutar el caso de uso: ${e.message}")
        }
    }


    private fun isInternetAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }
}