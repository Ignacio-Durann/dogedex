package com.app.dogedex.doglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.dogedex.databinding.ActivityDogListBinding
import com.app.dogedex.dogdetail.DogDetailActivity
import com.app.dogedex.dogdetail.DogDetailActivity.Companion.DOG_KEY

class DogListActivity : AppCompatActivity() {
    private val dogListViewModel: DogListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Infla el layout usando View Binding
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura el RecyclerView
        val recycler = binding.rvDogRecycler
        recycler.layoutManager = LinearLayoutManager(this)

        // Configura el Adaptador del RecyclerView
        val adapterD = DogAdapter()
        //se agrega el click al nombre
        adapterD.setOnItemClickListener {
            //pasasr dog a dogDetailActivity
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)
        }
        recycler.adapter = adapterD

        // Observa el LiveData de dogList para actualizar la lista en el adaptador
        dogListViewModel.dogList.observe(this) { dogList ->
            adapterD.submitList(dogList)
        }
    }
}
