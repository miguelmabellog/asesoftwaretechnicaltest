package com.miguel.asesoftwaretechnicaltest.presentation.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.miguel.asesoftwaretechnicaltest.data.PhotoEntity

import androidx.lifecycle.viewmodel.compose.viewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(photoId: Int?,navController: NavController, viewModel: DetailViewModel = viewModel()) {

    if(photoId!=null)
    viewModel.findPhotoById(photoId)

    val photoState by viewModel.state.collectAsState(initial = DetailState())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Photo Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        },
        content = { innerpadding ->
            Log.i("valorDePhoto",photoState.photo.toString())
            Column(modifier = Modifier.padding(innerpadding)) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    if (photoState.isLoading){
                        Text(text = "Loading")
                    }else{
                        val photo = photoState.photo
                        Text(text = "Album ID: ${photo.albumId}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "ID: ${photo.id}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Title: ${photo.title}")
                        Spacer(modifier = Modifier.height(8.dp))
                        ImageFromUrl(url = photo.url)
                    }

                }
            }
        }
    )



}


@Composable
fun ImageFromUrl(url: String?) {
    AsyncImage(
        model = url,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9)
    )

}