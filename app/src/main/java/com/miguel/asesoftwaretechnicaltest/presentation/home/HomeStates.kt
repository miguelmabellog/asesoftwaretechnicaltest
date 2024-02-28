package com.miguel.asesoftwaretechnicaltest.presentation.home

import com.miguel.asesoftwaretechnicaltest.repository.PhotoDomain

data class HomeStates (
    val isError:Boolean=false,
    val isLoading:Boolean=false,
    val photosState: List<PhotoDomain> = emptyList()

)