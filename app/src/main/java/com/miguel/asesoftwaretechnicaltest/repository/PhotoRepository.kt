package com.miguel.asesoftwaretechnicaltest.repository

import com.miguel.asesoftwaretechnicaltest.data.Ktorapiphoto

class PhotoRepository(val ktorapiphoto: Ktorapiphoto){
    companion object {
        val photoRepository = PhotoRepository(Ktorapiphoto.ktorapiphoto)
    }
    suspend fun getPhotos():List<PhotoDomain>{
        return Ktorapiphoto.ktorapiphoto.getPhotos().map {
            PhotoDomain(it.albumId,
                it.id,
                it.title,
                it.url,
                it.thumbnailUrl)
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

