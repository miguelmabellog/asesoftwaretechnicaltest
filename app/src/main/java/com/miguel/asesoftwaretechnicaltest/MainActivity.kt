package com.miguel.asesoftwaretechnicaltest

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.miguel.asesoftwaretechnicaltest.presentation.detail.DetailScreen
import com.miguel.asesoftwaretechnicaltest.presentation.detail.DetailViewModel
import com.miguel.asesoftwaretechnicaltest.presentation.home.HomeScreen
import com.miguel.asesoftwaretechnicaltest.presentation.home.HomeViewModel
import com.miguel.asesoftwaretechnicaltest.repository.PendingRequest

import com.miguel.asesoftwaretechnicaltest.ui.theme.AsesoftwaretechnicaltestTheme
import com.miguel.asesoftwaretechnicaltest.usecase.CheckPendingRequestByIdUseCase
import com.miguel.asesoftwaretechnicaltest.usecase.DeletePhotoUseCase
import com.miguel.asesoftwaretechnicaltest.usecase.Error
import com.miguel.asesoftwaretechnicaltest.usecase.GetAllPendingRequestUseCase
import com.miguel.asesoftwaretechnicaltest.usecase.GetPhotoByIdUseCase
import com.miguel.asesoftwaretechnicaltest.usecase.Success
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(MyAppViewModel.getInstance(this))
        }
    }
}



@Composable
fun MyApp(viewModel: MyAppViewModel = viewModel()) {
    val navController = rememberNavController()
    val context = LocalContext.current





    NavHost(navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        //Utilizar factory es una mejor practica
        composable("home") { HomeScreen(navController,HomeViewModel.getInstance(context)) }
        composable("detail/{photoId}") { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val photoId = arguments.getString("photoId")?.toInt()
            DetailScreen(photoId = photoId,navController, DetailViewModel.getInstance(context))
        }
    }
}


class MyAppViewModel(private val getAllPendingRequestUseCase: GetAllPendingRequestUseCase,
                     private val deletePhotoUseCase: DeletePhotoUseCase) : ViewModel() {
    private var allPendingRequest:List<PendingRequest> = emptyList()
    companion object {
        fun getInstance(context: Context): MyAppViewModel {
            val getAllPendingRequestUseCase:GetAllPendingRequestUseCase=GetAllPendingRequestUseCase.getInstance(context)
            val deletePhotoUseCase:DeletePhotoUseCase=DeletePhotoUseCase.getInstance(context)
            return MyAppViewModel(getAllPendingRequestUseCase,deletePhotoUseCase)
        }
    }
    init {
        viewModelScope.launch {
            getAllPendingDeleteRequest()
        }

    }
    private fun getAllPendingDeleteRequest(){
        viewModelScope.launch {

            when (val result = getAllPendingRequestUseCase.execute(Unit)) {
                is Success -> {
                    allPendingRequest=result.data
                    Log.i("AllPendingRequest",result.data.toString())
                    executeAllPendingRequest(allPendingRequest)
                }
                is Error -> {
                    Log.e("ErrorGettingAllPendingRequest",result.message)

                }
            }

        }
    }

    private fun executeAllPendingRequest(listPendingRequest: List<PendingRequest>){
        viewModelScope.launch {
            listPendingRequest.forEach {
                when (val result = deletePhotoUseCase.execute(it.photoId)) {
                    is Success -> {
                        Log.i("Execute Delete",it.photoId.toString())
                    }
                    is Error -> {
                        Log.e("ErrorDeletingPendingRequest",result.message)

                    }
                }
            }
        }

    }


}

@Composable
fun SplashScreen(navController: NavHostController) {

    LaunchedEffect(key1 = true) {
        delay(3000)
        navController.navigate("home")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "App Icon",
                modifier = Modifier.size(96.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Splash Screen", color = Color.Black)
        }
    }
}

