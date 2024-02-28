package com.miguel.asesoftwaretechnicaltest.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.miguel.asesoftwaretechnicaltest.repository.PhotoDomain

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {

    val screenstate by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Top app bar")
                }
            )
        },
        content = {innerpadding->
            Column(modifier = Modifier.padding(innerpadding)) {
                if (screenstate.isLoading){
                    Text(text = "is loading")
                }else{
                    if (screenstate.isError){
                        Text(text = "There is an error fetching the data")
                    }else{
                        LazyColumn {
                            items(screenstate.photosState) { photo ->
                                PhotoItem(photo = photo)
                            }
                        }
                    }
                }


            }
        }
    )
}

@Composable
fun PhotoItem(photo: PhotoDomain) {
    Row{
        AsyncImage(
            modifier = Modifier.size(100.dp),
            model = photo.url,
            contentDescription = photo.title
        )
        Column {
            Text(text = photo.title)
            Text(text = photo.url)
            Text(text = photo.id.toString())
        }
    }
    
}
