package com.miguel.asesoftwaretechnicaltest.repository

import android.content.Context
import com.miguel.asesoftwaretechnicaltest.data.KtorApiInterface
import com.miguel.asesoftwaretechnicaltest.data.Ktorapiphoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotoRepository(private val context: Context,private val ktorapiphoto: KtorApiInterface) {
    private val photoLocalDatabase: PhotoLocalDatabase by lazy {
        PhotoLocalDatabase.getInstance(context)
    }

    private val pendingRequestDatabase: AppDatabase by lazy {
        AppDatabase.getInstance(context)
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
        val photoEntity = photoLocalDatabase.photoDao().getPhotoById(photoId)

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
            photoLocalDatabase.photoDao().insertPhotos(entities)
        }
    }

    suspend fun savePendingDeleteByID(photoId: Int){
        withContext(Dispatchers.IO) {
            pendingRequestDatabase.pendingRequestDao().insert(PendingRequest(photoId = photoId))
        }
    }

    suspend fun getPendingDeleteById(photoId: Int): PendingRequest? {
        return pendingRequestDatabase.pendingRequestDao().getPendingRequestByPhotoId(photoId)
    }

    suspend fun getAllPendingRequest(): List<PendingRequest> {
        return pendingRequestDatabase.pendingRequestDao().getAllPendingRequests()
    }

    suspend fun deletePendingRequestById(photoId: Int){
        return pendingRequestDatabase.pendingRequestDao().deletePendingRequestsByPhotoId(photoId)
    }
}

data class PhotoDomain(
    val albumId:Int,
    val id:Int,
    val title:String,
    val url:String,
    val thumbnailUrl:String
)

