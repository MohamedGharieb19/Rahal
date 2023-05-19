package com.example.rahal.ui.busniessowner

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.Navigation
import com.example.rahal.*
import com.example.rahal.api.HomeApi
import com.example.rahal.data.UserRequest
import com.example.rahal.data.UserResponse
import com.example.rahal.databinding.FragmentLogInBusinessOwnerBinding
import com.example.rahal.module.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LogInBusinessOwnerFragment : Fragment() {
    lateinit var binding: FragmentLogInBusinessOwnerBinding
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var emailErrorMessage: TextView
    private lateinit var passwordErrorMessage: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLogInBusinessOwnerBinding.inflate(inflater,container,false)

        initializeVariables()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateEmail()
        validatePassword()

        binding.backArrowButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.landingPageFragment)
        }

        binding.registerNowTextVeiw.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.signUpBusinessOwnerFragment)
        }

        binding.forgetPasswordTextView.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.forgetPasswordBusinessOwnerFragment)
        }

        binding.floatingButton.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val request = UserRequest()
        request.email = emailEditText.text.toString().trim()
        request.password = passwordEditText.text.toString().trim()

        val retrofit = Retrofit().getRetrofitClientInstance().create(HomeApi::class.java)
        retrofit.login(request).enqueue(object: Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val user = response.body()
                if (response.code() == 201 && user?.data?.user?.role.toString() == "business_owner" ) {
                    Log.e("sucess", user?.token.toString())
                    Log.e("sucess", user?.data?.user?.role.toString())
                    Toast.makeText(activity,"login sucess",Toast.LENGTH_LONG).show()
                }else {
                    Toast.makeText(activity,"invalid mail or password",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("Error" ,t.message.toString())
            }

        })
    }

    private fun initializeVariables(){
        emailEditText = binding.emailEditText
        emailErrorMessage = binding.errorMessageValidEmail

        passwordEditText = binding.passwordEditText
        passwordErrorMessage = binding.errorMessagePassword
    }

    private fun showErrorMessage(editText: EditText, textView: TextView, errorMessage: String){
        editText.error = errorMessage
        textView.text = errorMessage
    }

    private fun validateEmail(){
        val errorMessage = "Enter a valid email"

        emailEditText.doAfterTextChanged {
            if (emailEditText.text.toString().isNotEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.toString()).matches()) {
                emailErrorMessage.text = ""
            }else {
                showErrorMessage(emailEditText,emailErrorMessage,errorMessage)
            }
        }
    }

    private fun validatePassword(){
        val errorMessage = "password should be 8 character or more"

        passwordEditText.doAfterTextChanged {
            // if password length is less than 8
            if (passwordEditText.text.toString().length < 8){
                showErrorMessage(passwordEditText,passwordErrorMessage,errorMessage)
            }else{
                passwordErrorMessage.text = ""
            }
        }
    }


}