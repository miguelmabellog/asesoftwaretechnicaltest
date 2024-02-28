package com.miguel.asesoftwaretechnicaltest.presentation.home


import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguel.asesoftwaretechnicaltest.usecase.Error
import com.miguel.asesoftwaretechnicaltest.usecase.FetchPhotosUseCase
import com.miguel.asesoftwaretechnicaltest.usecase.Success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val fetchPhotosUseCase: FetchPhotosUseCase ) : ViewModel() {

    companion object {
        fun getInstance(context: Context): HomeViewModel {
            val fetchPhotosUseCase = FetchPhotosUseCase.getInstance(context)
            return HomeViewModel(fetchPhotosUseCase)
        }
    }

    private val _state = MutableStateFlow(HomeStates())
    val state: StateFlow<HomeStates> = _state

    init {
        fetchPhotos()
    }

    private fun fetchPhotos() {
        viewModelScope.launch {
            _state.value=_state.value.copy(isLoading = true)
            val result = fetchPhotosUseCase.execute(Unit)
            when (result) {
                is Success -> {
                    val photos = result.data
                    _state.value = _state.value.copy(photosState = photos)
                }
                is Error -> {
                    Log.e("ErrorFetch",result.message)
                    _state.value=_state.value.copy(isError = true)
                }
            }
            _state.value=_state.value.copy(isLoading = false)
        }
    }


}