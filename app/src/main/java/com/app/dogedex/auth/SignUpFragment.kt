package com.app.dogedex.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.dogedex.R
import com.app.dogedex.databinding.FragmentSignUpBinding
import com.app.dogedex.utils.isValidEmail


class SignUpFragment : Fragment() {

private lateinit var binding: FragmentSignUpBinding

    interface SignUpFragmentActions{
        fun onSignUpFieldValidated(email: String, password: String, confimationPassword: String)
    }

    private lateinit var signUpFragmentActions: SignUpFragmentActions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpFragmentActions = try {
            context as SignUpFragmentActions
        }catch (e: ClassCastException){
            throw ClassCastException ("$context must implement LoginFragmentActions")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater)
        setupSignUpButton()
        return binding.root
    }

    private fun setupSignUpButton() {
        binding.signUpButton.setOnClickListener {
            validateField()
        }
    }

    private fun validateField() {
        cleanInputs()
        val email = binding.emailEdit.text.toString()
        if (!isValidEmail(email)){
            binding.emailInput.error = getString(R.string.email_is_not_valid)
        }
        val  password = binding.passwordEdit.text.toString()
        if (password.isEmpty()){
            binding.passwordInput.error = getString(R.string.password_must_not_be_empty)
        }
        val  passwordConfirmation = binding.confirmPasswordEdit.text.toString()
        if (passwordConfirmation.isEmpty()){
            binding.confirmPasswordInput.error = getString(R.string.password_must_not_be_empty)
        }

        if (password != passwordConfirmation){
            binding.passwordInput.error = getString(R.string.password_do_not_match)
        }

        //sign up
        if (email.isNotEmpty() && password.isNotEmpty() && passwordConfirmation.isNotEmpty()){
            signUpFragmentActions.onSignUpFieldValidated(email, password, passwordConfirmation)
        }else{
            Toast.makeText(context, getString(R.string.some_field_is_empty_make_sure_to_fill_them), Toast.LENGTH_SHORT).show()
        }

    }

    private fun cleanInputs() {
        binding.emailInput.error = ""
        binding.passwordInput.error = ""
        binding.confirmPasswordInput.error = ""
    }


}