package com.miguel.asesoftwaretechnicaltest.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.miguel.asesoftwaretechnicaltest.data.PhotoEntity


@Composable
fun DetailScreen(photo: PhotoEntity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Album ID: ${photo.albumId}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "ID: ${photo.id}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Title: ${photo.title}")
        Spacer(modifier = Modifier.height(8.dp))
        ImageFromUrl(url = photo.url)
    }
}

@Composable
fun ImageFromUrl(url: String) {
    AsyncImage(
        model = url,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f/9)
    )

}