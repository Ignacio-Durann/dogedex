package com.app.dogedex.dogdetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.dogedex.R
import com.app.dogedex.api.response.Dog
import com.app.dogedex.databinding.ActivityDogDetailBinding

class DogDetailActivity : AppCompatActivity() {
    companion object{
        const val DOG_KEY = "dog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dog = intent?.extras?.getParcelable<Dog>(DOG_KEY)

        if (dog == null){
            Toast.makeText(this, R.string.error_showing_dog_not_found, Toast.LENGTH_SHORT)
            finish()
            return
        }
        binding.dogIndex.text = getString(R.string.dog_index_format, dog.index)
        binding.lifeExpectancy.text = getString( R.string.dog_life_expentancy_format, dog.lifeExpentancy)
        binding.dog = dog
    }
}