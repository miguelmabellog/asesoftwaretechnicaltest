package com.miguel.asesoftwaretechnicaltest.presentation.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguel.asesoftwaretechnicaltest.data.PhotoEntity
import com.miguel.asesoftwaretechnicaltest.repository.PhotoDomain
import com.miguel.asesoftwaretechnicaltest.usecase.Error
import com.miguel.asesoftwaretechnicaltest.usecase.GetPhotoByIdUseCase
import com.miguel.asesoftwaretechnicaltest.usecase.Success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val getPhotoByIdUseCase: GetPhotoByIdUseCase) : ViewModel() {
    companion object {
        fun getInstance(context: Context): DetailViewModel {
            val getPhotoByIdUseCase = GetPhotoByIdUseCase.getInstance(context)
            return DetailViewModel(getPhotoByIdUseCase)
        }
    }

    private val _state = MutableStateFlow(DetailState(isLoading = false, isError = false, photo = PhotoDomain(albumId = 0, id = 0, title = "", url = "", thumbnailUrl = "")))
    val state: StateFlow<DetailState> = _state




    fun findPhotoById(photoId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
             when (val result = getPhotoByIdUseCase.execute(photoId)) {
                is Error -> {
                    Log.e("ErrorGetById", result.message)
                    _state.value = _state.value.copy(isError = true, isLoading = false)
                }
                is Success -> {
                    val photo: PhotoDomain = result.data
                    _state.value = _state.value.copy(photo = photo, isLoading = false)
                }
            }

        }

    }

}