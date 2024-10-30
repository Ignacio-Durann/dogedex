package com.app.dogedex.doglist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dogedex.Dog
import com.app.dogedex.R
import com.app.dogedex.databinding.ActivityDogListBinding

class DogListActivity : AppCompatActivity() {
    private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler = binding.rvDogRecycler
        recycler.layoutManager = LinearLayoutManager(this)

        val adapter = DogAdapter()
        recycler.adapter = adapter
        dogListViewModel.dogList.observe(this){dogList ->
            adapter.submitList(dogList)
        }

    }


}