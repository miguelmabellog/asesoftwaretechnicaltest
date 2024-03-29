package com.miguel.asesoftwaretechnicaltest.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.call.receive
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


import kotlinx.serialization.json.JsonArray

class Ktorapiphoto:KtorApiInterface {
    companion object {
        val ktorapiphoto = Ktorapiphoto()
    }

    private val client = HttpClient(CIO) {
        install(ContentNegotiation){
            json()

        }
    }

    override suspend fun getPhotos(): List<PhotoEntity> {
        val response: HttpResponse = client.get("https://jsonplaceholder.typicode.com/photos")
        val dataArray: Array<PhotoEntity> = Json.decodeFromString(response.call.body())
        return dataArray.toList()
    }

    override suspend fun deletePhoto(photoId: Int) {
        val response:HttpResponse=client.delete("https://jsonplaceholder.typicode.com/photos/$photoId")
    }

}



@Serializable
data class PhotoEntity(
    val albumId:Int,
    val id:Int,
    val title:String,
    val url:String,
    val thumbnailUrl:String
)