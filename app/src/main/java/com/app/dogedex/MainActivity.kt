package com.app.dogedex

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.dogedex.api.ApiServiceInterceptor
import com.app.dogedex.auth.LoginActivity
import com.app.dogedex.databinding.ActivityMainBinding
import com.app.dogedex.doglist.DogListActivity
import com.app.dogedex.model.User
import com.app.dogedex.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = User.getLoggedInUser(this)
        if (user == null){
            openLoginActivity()
            return
        }else{
            ApiServiceInterceptor.setSessionToken(user.authenticationToken)
        }


        binding.settingsFab.setOnClickListener {
            openSettingsActivity()

        }

        binding.dogListFab.setOnClickListener {
            openDogListActivity()
        }

    }

    private fun openDogListActivity() {
        startActivity(Intent(this, DogListActivity::class.java))
    }

    private fun openSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun openLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}