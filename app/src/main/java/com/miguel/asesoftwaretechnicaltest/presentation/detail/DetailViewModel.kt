package com.miguel.asesoftwaretechnicaltest.presentation.detail

import androidx.lifecycle.ViewModel
import com.miguel.asesoftwaretechnicaltest.data.PhotoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DetailViewModel : ViewModel() {
    private val _photoState = MutableStateFlow<PhotoEntity?>(null)
    val photoState: StateFlow<PhotoEntity?> = _photoState

    fun findPhotoById(photoId: Int) {
        // Aquí deberías implementar la lógica para buscar la foto por su ID.
        // Por ahora, solo devolveremos una foto de ejemplo.
        val photo = listOf(
            PhotoEntity(1, 1, "Photo 1", "https://via.placeholder.com/1", "https://via.placeholder.com/1"),
            PhotoEntity(2, 2, "Photo 2", "https://via.placeholder.com/2", "https://via.placeholder.com/2"),
            PhotoEntity(3, 3, "Photo 3", "https://via.placeholder.com/3", "https://via.placeholder.com/3")
        ).find { it.id == photoId }
        _photoState.value = photo
    }
}