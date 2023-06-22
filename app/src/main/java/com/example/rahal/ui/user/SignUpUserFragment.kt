package com.example.rahal.ui.user

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.rahal.*
import com.example.rahal.api.HomeApi
import com.example.rahal.data.RegisterUserRequest
import com.example.rahal.data.UserResponse
import com.example.rahal.databinding.FragmentSignUpUserBinding
import com.example.rahal.module.Retrofit
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

@AndroidEntryPoint
class SignUpUserFragment : Fragment() {
    lateinit var binding: FragmentSignUpUserBinding
    private lateinit var fullNameEditText:EditText
    private lateinit var validateEmailEditText:EditText
    private lateinit var passwordEditText:EditText
    private lateinit var verifyPasswordEditText:EditText
    private lateinit var fullNameErrorMessage:TextView
    private lateinit var validateEmailErrorMessage:TextView
    private lateinit var passwordErrorMessage:TextView
    private lateinit var verifyPasswordErrorMessage:TextView

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpUserBinding.inflate(inflater,container,false)

        initializeVariables()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateFullName()
        validateEmail()
        validatePassword()
        validateVerifyPassword()

        binding.backArrowButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.logInUserFragment)
        }
        binding.logInTextVeiw.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.logInUserFragment)
        }

        binding.floatingButton.setOnClickListener {
//            if (fullNameEditText.text.toString().isNotEmpty() &&
//                validateEmailEditText.text.toString().isNotEmpty() &&
//                passwordEditText.text.toString().isNotEmpty() &&
//                verifyPasswordEditText.text.toString().isNotEmpty() ){
//
//                Navigation.findNavController(view).navigate(R.id.followSignUpUserFragment)
//            }

            signup()
        }

    }

    private fun sendData(){
        val fragment = FollowSignUpUserFragment()
        val bundle = Bundle()
        bundle.putString("fullName",fullNameEditText.text.toString().trim())
        bundle.putString("email",validateEmailEditText.text.toString().trim())
        bundle.putString("password",passwordEditText.text.toString().trim())
        bundle.putString("passwordConfirm",verifyPasswordEditText.text.toString().trim())
        bundle.putString("role","user")
        fragment.arguments = bundle
        findNavController().navigate(R.id.followSignUpUserFragment,bundle)
    }

    private fun signup() {
        val role = "user"
        val response = RegisterUserRequest()

        response.fullName = fullNameEditText.text.toString().trim()
        response.email = validateEmailEditText.text.toString().trim()
        response.password = passwordEditText.text.toString().trim()
        response.passwordConfirm = verifyPasswordEditText.text.toString().trim()
        response.role = role


        val retrofit = Retrofit().getRetrofitClientInstance().create(HomeApi::class.java)
        retrofit.signup(response).enqueue(object: Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val user = response.body()
                if (response.code() == 201) {
                    Log.e("success token", user?.token.toString())
                    Log.e("success", user?.data?.user?.name.toString())
                    Log.e("success", user?.data?.user?.email.toString())
                    Log.e("success", user?.data?.user?.password.toString())
                    Log.e("success", user?.data?.user?.role.toString())
                    Toast.makeText(activity,"Register success", Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("Error" ,t.message.toString())
                Toast.makeText(activity,"Register Failed",Toast.LENGTH_LONG).show()

            }

        })
    }

    private fun initializeVariables(){
        fullNameEditText = binding.fullNameEditText
        fullNameErrorMessage = binding.errorMessageFullName

        validateEmailEditText = binding.validEmailEditText
        validateEmailErrorMessage = binding.errorMessageValidEmail

        passwordEditText = binding.passwordEditText
        passwordErrorMessage = binding.errorMessagePassword

        verifyPasswordEditText = binding.verifyPasswordEditText
        verifyPasswordErrorMessage = binding.errorMessageVerifyPassword
    }

    private fun showErrorMessage(editText: EditText, textView: TextView, errorMessage: String){
        editText.error = errorMessage
        textView.text = errorMessage
    }

    private fun validateFullName(){
        val errorMessage = "Name is required"

        fullNameEditText.doOnTextChanged { _, _, _, _ ->
            fullNameErrorMessage.text = ""
        }
        fullNameEditText.doAfterTextChanged {
            if (fullNameEditText.text.toString() == ""){
                showErrorMessage(fullNameEditText,fullNameErrorMessage, errorMessage)
            }
        }
    }

    private fun validateEmail(){
        val errorMessage = "Enter a valid email"

        validateEmailEditText.doAfterTextChanged {
            if (validateEmailEditText.text.toString().isNotEmpty() &&
            Patterns.EMAIL_ADDRESS.matcher(validateEmailEditText.text.toString()).matches()) {
                validateEmailErrorMessage.text = ""
            }else {
                showErrorMessage(validateEmailEditText,validateEmailErrorMessage,errorMessage)
            }
        }
    }

    private fun validatePassword(){
        val errorMessage = "password should contain a-z , A-z , 0-9"
        val uppercase: Pattern = Pattern.compile("[A-Z]")
        val lowercase: Pattern = Pattern.compile("[a-z]")
        val digit: Pattern = Pattern.compile("[0-9]")

        passwordEditText.doAfterTextChanged {
                // if lowercase character is not present
            if(!lowercase.matcher(passwordEditText.text.toString()).find()){
                showErrorMessage(passwordEditText,passwordErrorMessage,errorMessage)
            }else{
                passwordErrorMessage.text = ""
            }

                // if uppercase character is not present
            if (!uppercase.matcher(passwordEditText.text.toString()).find()){
                showErrorMessage(passwordEditText,passwordErrorMessage,errorMessage)
            }else{
                passwordErrorMessage.text = ""
            }

                // if digit is not present
            if (!digit.matcher(passwordEditText.text.toString()).find()){
                showErrorMessage(passwordEditText,passwordErrorMessage,errorMessage)
            }else{
                passwordErrorMessage.text = ""
            }

                // if password length is less than 8
            if (passwordEditText.text.toString().length < 8){
                showErrorMessage(passwordEditText,passwordErrorMessage,errorMessage)
            }else{
                passwordErrorMessage.text = ""
            }
        }
    }

    private fun validateVerifyPassword(){
        val errorMessage = "Rematch password"
        verifyPasswordEditText.doAfterTextChanged {
            if (passwordEditText.text.toString() == verifyPasswordEditText.text.toString() ){
                verifyPasswordErrorMessage.text = ""
            }else {
                showErrorMessage(verifyPasswordEditText,verifyPasswordErrorMessage,errorMessage)
            }
        }
    }

}