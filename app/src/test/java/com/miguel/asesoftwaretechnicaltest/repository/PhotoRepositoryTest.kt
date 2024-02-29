package com.miguel.asesoftwaretechnicaltest.repository

import android.content.Context
import com.miguel.asesoftwaretechnicaltest.data.KtorApiInterface
import com.miguel.asesoftwaretechnicaltest.data.PhotoEntity
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test


class PhotoRepositoryTest {

    @Test
    fun `test getPhotos`() {
        // Arrange
        val mockKtorApiPhoto = MockKtorApiPhoto()
        val mockContext = mockk<Context>()
        val repository = PhotoRepository(mockContext,mockKtorApiPhoto)

        // Act
        val result = runBlocking { repository.getPhotos() }

        // Assert
        val mockPhotoDomain = listOf(
            PhotoDomain(1, 1, "Title 1", "http://example.com/photo1.jpg", "http://example.com/thumbnail1.jpg"),
            PhotoDomain(2, 2, "Title 2", "http://example.com/photo2.jpg", "http://example.com/thumbnail2.jpg")
        )
        assertEquals(mockPhotoDomain, result)
    }

    private class MockKtorApiPhoto : KtorApiInterface {
        val mockPhotoEntity = listOf(
            PhotoEntity(1, 1, "Title 1", "http://example.com/photo1.jpg", "http://example.com/thumbnail1.jpg"),
            PhotoEntity(2, 2, "Title 2", "http://example.com/photo2.jpg", "http://example.com/thumbnail2.jpg")
        )

        override suspend fun getPhotos(): List<PhotoEntity> {
            return mockPhotoEntity
        }

        override suspend fun deletePhoto(photoId: Int) {

        }

    }
}