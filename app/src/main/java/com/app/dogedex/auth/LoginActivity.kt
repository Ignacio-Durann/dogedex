package com.app.dogedex.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.app.dogedex.MainActivity
import com.app.dogedex.R
import com.app.dogedex.api.ApiResponseStatus
import com.app.dogedex.databinding.ActivityLoginBinding
import com.app.dogedex.model.User

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmentActions,
    SignUpFragment.SignUpFragmentActions {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.status.observe(this) { status ->

            when (status) {
                is ApiResponseStatus.Error -> {
                    //muestra la error en la datos
                    showMessageDialog(status.messageId)
                    // ocultar el progress bar
                    binding.loadingWheel.visibility = View.GONE
                }

                is ApiResponseStatus.Loading -> {
                    //muestra la carga de datos
                    binding.loadingWheel.visibility = View.VISIBLE
                }

                is ApiResponseStatus.Success -> {
                    //muestra la carga de datos ya finalizada
                    // ocultar el progress bar
                    binding.loadingWheel.visibility = View.GONE
                }
            }
        }

        viewModel.user.observe(this) { user ->
            if (user != null) {
                User.setLoggedInUser(this, user)
                startMainActivity()
            }
        }

    }

    private fun startMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onRegistrerButtonClick() {
        findNavController(R.id.nav_host_fragment)
            .navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
    }

    override fun onLoginFieldValidated(email: String, password: String) {
        viewModel.login(email, password)

    }

    override fun onSignUpFieldValidated(
        email: String,
        password: String,
        confimationPassword: String
    ) {
        viewModel.signUp(email, password, confimationPassword)
    }

    private fun showMessageDialog(messageId: Int) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.there_was_an_error))
            .setMessage(messageId)
            .setPositiveButton(getString(R.string.ok)) { _, _, -> /** Dismis dialog **/ }
            .create()
            .show()
    }
}