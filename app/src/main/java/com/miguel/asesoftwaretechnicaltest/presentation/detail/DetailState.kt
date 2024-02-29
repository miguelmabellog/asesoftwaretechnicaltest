package com.miguel.asesoftwaretechnicaltest.presentation.detail

import com.miguel.asesoftwaretechnicaltest.repository.PhotoDomain

data class DetailState (
    val isLoading:Boolean=false,
    val isError:Boolean=false,
    val photo:PhotoDomain = PhotoDomain(0,0,"","",""),
    val showSnackBar:Boolean=false,
    val dataDeleted:Boolean=false
)