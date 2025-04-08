package com.app.dogedex.dogdetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.app.dogedex.R
import com.app.dogedex.api.ApiResponseStatus
import com.app.dogedex.model.Dog
import com.app.dogedex.databinding.ActivityDogDetailBinding


class DogDetailActivity : AppCompatActivity() {
    companion object{
        const val DOG_KEY = "dog"
        const val IS_RECOGNITION_KEY = "is_recognition"
    }

    private val viewModel: DogDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)
        val isRecognition = intent?.extras?.getBoolean(IS_RECOGNITION_KEY, false) ?: false

        if (dog == null) {
            Toast.makeText(this, R.string.error_showing_dog_not_found, Toast.LENGTH_SHORT)
            finish()
            return
        }
        binding.dogIndex.text = getString(R.string.dog_index_format, dog.index)
        binding.lifeExpectancy.text =
            getString(R.string.dog_life_expentancy_format, dog.lifeExpentancy)
        binding.dog = dog
        binding.dogImage.load(dog.imageUrl)

        viewModel.status.observe(this) { status ->

            when (status) {
                is ApiResponseStatus.Error -> {
                    //muestra la error en la datos
                    Toast.makeText(this, getString(status.messageId), Toast.LENGTH_SHORT)
                    // ocultar el progress bar
                    binding.loadingWheel.visibility = View.GONE
                }

                is ApiResponseStatus.Loading -> {
                    //muestra la carga de datos
                    binding.loadingWheel.visibility = View.VISIBLE
                }

                is ApiResponseStatus.Success -> {
                    //muestra la carga de datos ya finalizada
                    // ocultar el progress bar y finaliza el activity
                    binding.loadingWheel.visibility = View.GONE
                    finish()
                }
            }

            binding.closeButton.setOnClickListener {
                if (isRecognition) {
                    viewModel.addDogToUser(dog.id)
                } else {
                    finish()
                }
            }
        }
    }
}