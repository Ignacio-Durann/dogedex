package com.app.dogedex.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.annotation.ExperimentalCoilApi
import com.app.dogedex.R
import com.app.dogedex.api.ApiResponseStatus
import com.app.dogedex.dogdetail.ui.theme.DogedexTheme
import com.app.dogedex.model.Dog

@ExperimentalCoilApi
class DogDetailComposeActivity : ComponentActivity() {
    companion object {
        const val DOG_KEY = "dog"
        const val IS_RECOGNITION_KEY = "is_recognition"
    }

    private val viewModel: DogDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        val isRecognition = intent?.extras?.getBoolean(IS_RECOGNITION_KEY, false) ?: false

        if (dog == null) {
            Toast.makeText(this, R.string.error_showing_dog_not_found, Toast.LENGTH_SHORT)
            finish()
            return
        }
        setContent {
            val status = viewModel.status
            if(status.value is ApiResponseStatus.Success){
                finish()
            }else{
                DogedexTheme {
                    DogDetailScreen(
                        dog = dog, status = status.value, onButtonClicked = {
                            onButtonClicked(dog.id, isRecognition)
                        })
                }
            }
        }
    }

    private fun onButtonClicked(dogId: Long, isRecognition: Boolean) {
        if (isRecognition){
            viewModel.addDogToUser(dogId)
        }else{
            finish()
        }
    }
}
