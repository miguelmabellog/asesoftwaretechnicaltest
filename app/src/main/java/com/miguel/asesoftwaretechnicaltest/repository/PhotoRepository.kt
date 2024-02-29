package com.miguel.asesoftwaretechnicaltest.repository

import android.content.Context
import androidx.room.Room
import com.miguel.asesoftwaretechnicaltest.data.KtorApiInterface
import com.miguel.asesoftwaretechnicaltest.data.Ktorapiphoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotoRepository(private val context: Context,private val ktorapiphoto: KtorApiInterface) {
    private val photoDatabase: PhotoDatabase by lazy {
        PhotoDatabase.getInstance(context)
    }

    companion object {
        fun getInstance(context: Context): PhotoRepository {
            return PhotoRepository(context, Ktorapiphoto.ktorapiphoto)
        }
    }


    suspend fun getPhotos(): List<PhotoDomain> {
        return ktorapiphoto.getPhotos().map {
            PhotoDomain(it.albumId, it.id, it.title, it.url, it.thumbnailUrl)
        }
    }

    suspend fun deletePhoto(photoId: Int) {
        ktorapiphoto.deletePhoto(photoId)
    }

    suspend fun getPhotoById(photoId: Int): PhotoDomain {
        val photoEntity = photoDatabase.photoDao().getPhotoById(photoId)

        return PhotoDomain(
                albumId = photoEntity?.albumId ?: 0,
                id = photoEntity?.id ?: 0,
                title = photoEntity?.title?: "",
                url = photoEntity?.url?:"",
                thumbnailUrl = photoEntity?.thumbnailUrl?:""
            )

    }

    suspend fun savePhotosLocally(photos: List<PhotoDomain>) {
        val entities = photos.map {
            PhotoEntity(it.id, it.albumId, it.title, it.url, it.thumbnailUrl)
        }
        withContext(Dispatchers.IO) {
            photoDatabase.photoDao().insertPhotos(entities)
        }
    }
}

data class PhotoDomain(
    val albumId:Int,
    val id:Int,
    val title:String,
    val url:String,
    val thumbnailUrl:String
)

