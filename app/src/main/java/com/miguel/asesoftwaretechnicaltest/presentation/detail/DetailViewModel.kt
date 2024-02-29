package com.miguel.asesoftwaretechnicaltest.presentation.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miguel.asesoftwaretechnicaltest.repository.PendingRequest
import com.miguel.asesoftwaretechnicaltest.repository.PhotoDomain
import com.miguel.asesoftwaretechnicaltest.usecase.CheckPendingRequestByIdUseCase
import com.miguel.asesoftwaretechnicaltest.usecase.DeletePhotoUseCase
import com.miguel.asesoftwaretechnicaltest.usecase.Error
import com.miguel.asesoftwaretechnicaltest.usecase.GetPhotoByIdUseCase
import com.miguel.asesoftwaretechnicaltest.usecase.Success
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val getPhotoByIdUseCase: GetPhotoByIdUseCase,
                      private val deletePhotoUseCase: DeletePhotoUseCase,
                      private val checkPendingRequestByIdUseCase: CheckPendingRequestByIdUseCase) : ViewModel() {
    companion object {
        fun getInstance(context: Context): DetailViewModel {
            val getPhotoByIdUseCase = GetPhotoByIdUseCase.getInstance(context)
            val deletePhotoUseCase= DeletePhotoUseCase.getInstance(context)
            val checkPendingRequestByIdUseCase=CheckPendingRequestByIdUseCase.getInstance(context)
            return DetailViewModel(getPhotoByIdUseCase,deletePhotoUseCase,checkPendingRequestByIdUseCase)
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


    private fun checkIfIsPendingToDelete(photoId: Int):Boolean {

        var isPendingToDelete:Boolean=false
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            when (val result = checkPendingRequestByIdUseCase.execute(photoId)) {
                is Error -> {
                    Log.e("ErrorGetGetPendingRequestId", result.message)
                    _state.value = _state.value.copy(isError = true, isLoading = false)
                }
                is Success -> {
                    val pendingRequest: PendingRequest? = result.data
                    if (pendingRequest!=null){
                        isPendingToDelete=true
                        _state.value = _state.value.copy(isPendingToDelete = true,
                            isLoading = false,
                            showSnackBar = true)
                    }else{
                        isPendingToDelete=false
                        _state.value = _state.value.copy(isPendingToDelete = false, isLoading = false)
                    }

                }
            }

        }
        return isPendingToDelete

    }


    fun deletePhotoById(photoId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            if(!checkIfIsPendingToDelete(photoId)){
                when (val result = deletePhotoUseCase.execute(photoId)) {
                    is Error -> {
                        Log.e("Error Delete photo", result.message)
                        _state.value = _state.value.copy(isError = true,
                            isLoading = false,
                            dataDeleted = false,
                            showSnackBar = true)
                    }
                    is Success -> {
                        _state.value = _state.value.copy(isError = false,
                            isLoading = false,
                            dataDeleted = true,
                            showSnackBar = true,
                            isPendingToDelete = result.data)
                    }
                }
            }



        }

    }

    fun resetSnackBar(){
        _state.value = _state.value.copy(isError = false, isLoading = false, dataDeleted = false, showSnackBar = false)
    }

}