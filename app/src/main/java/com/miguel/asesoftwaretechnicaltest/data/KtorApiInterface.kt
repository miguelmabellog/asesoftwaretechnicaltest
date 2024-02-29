package com.miguel.asesoftwaretechnicaltest.data

interface KtorApiInterface {
    suspend fun getPhotos(): List<PhotoEntity>

    suspend fun deletePhoto(photoId: Int)
}