package com.app.dogedex.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.app.dogedex.R
import com.app.dogedex.databinding.FragmentLoginBinding
import com.app.dogedex.utils.isValidEmail

class LoginFragment : Fragment() {
    private lateinit var loginFragmentActions: LoginFragmentActions
    private lateinit var binding: FragmentLoginBinding


    interface LoginFragmentActions {
        fun onRegistrerButtonClick()
        fun onLoginFieldValidated(email: String, password: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginFragmentActions = try {
            context as LoginFragmentActions
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement LoginFragmentActions")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)
        binding.loginRegisterButton.setOnClickListener {
            loginFragmentActions.onRegistrerButtonClick()
        }
        binding.loginButton.setOnClickListener {
            validateFields()
            val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = requireActivity().currentFocus ?: View(requireActivity())
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
        return binding.root
    }

    private fun validateFields() {
        cleanInputs()
        val email = binding.emailEdit.text.toString()
        val password = binding.passwordEdit.text.toString()

        if (!isValidEmail(email)) {
            binding.emailInput.error = getString(R.string.email_is_not_valid)
        }

        if (password.isEmpty()) {
            binding.passwordInput.error = getString(R.string.password_must_not_be_empty)
        }

        if (password.isNotEmpty() && email.isNotEmpty()){
            loginFragmentActions.onLoginFieldValidated(email, password)
        }else{
            Toast.makeText(context, getString(R.string.some_field_is_empty_make_sure_to_fill_them), Toast.LENGTH_SHORT).show()

        }

    }

    private fun cleanInputs() {
        binding.emailInput.error = ""
        binding.passwordInput.error = ""
    }
}