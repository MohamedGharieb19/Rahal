package com.example.rahal.ui.user

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.Navigation
import com.example.rahal.R
import com.example.rahal.api.HomeApi
import com.example.rahal.data.RegisterUserRequest
import com.example.rahal.data.UserResponse
import com.example.rahal.databinding.FragmentFollowSignUpUserBinding
import com.example.rahal.module.Retrofit
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

@AndroidEntryPoint
class FollowSignUpUserFragment : Fragment(){
    lateinit var binding: FragmentFollowSignUpUserBinding
    private lateinit var phoneEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var birthDateEditText: EditText
    private lateinit var genderFieldEditText: AutoCompleteTextView
    private lateinit var phoneErrorMessage: TextView
    private lateinit var cityErrorMessage: TextView
    private lateinit var birthDateErrorMessage: TextView
    private lateinit var genderFieldErrorMessage: TextView
    private lateinit var email:String
    private lateinit var role:String
    private lateinit var fullName:String
    private lateinit var password:String
    private lateinit var confirmPassword:String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowSignUpUserBinding.inflate(inflater,container,false)

        intilaizeVariable()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)// Calendar
           showDatePicker()
           customSpinner()
           validatePhoneNumber()
           validateCity()
           validateBirthDate()
           //validateGender()
        binding.backArrowButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.signUpUserFragment)
        }

        binding.logInTextVeiw.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.logInUserFragment)
        }

        binding.floatingButton.setOnClickListener {
            signup()
        }


    }

    private fun intilaizeVariable(){
        phoneEditText = binding.phoneNumberEditText
        phoneErrorMessage = binding.errorMessagePhoneNumber

        cityEditText = binding.cityEditText
        cityErrorMessage = binding.errorMessageCity

        birthDateEditText = binding.dateOfBirthEditText
        birthDateErrorMessage = binding.errorMessageBirthDate

        genderFieldEditText = binding.spinnerAutoComplete
        genderFieldErrorMessage = binding.errorMessageGender
    }

    private fun getData(){
        val data = arguments
        if (data != null){
            email = data.getString("email").toString()
            fullName = data.getString("fullName").toString()
            role = data.getString("role").toString()
            password = data.getString("password").toString()
            confirmPassword = data.getString("passwordConfirm").toString()
        }
    }

    private fun signup() {
        getData()
        val response = RegisterUserRequest()

        response.fullName = fullName
        response.email = email
        response.password = password
        response.passwordConfirm = confirmPassword
        response.role = role
        response.gender = genderFieldEditText.text.toString().trim()
        response.phoneNumber = phoneEditText.text.toString().trim()
        response.birthDate = birthDateEditText.text.toString().trim()
        response.city = cityEditText.text.toString().trim()


        val retrofit = Retrofit().getRetrofitClientInstance().create(HomeApi::class.java)
        retrofit.signup(response).enqueue(object: Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val user = response.body()
                if (response.code() == 201) {
                    Log.e("success token", user?.token.toString())
                    Log.e("success", user?.data?.user?.name.toString())
                    Log.e("success", user?.data?.user?.email.toString())
                    Log.e("success", user?.data?.user?.password.toString())
                    Log.e("success", user?.data?.user?.role.toString())
                    Log.e("success", user?.data?.user?.phoneNumber.toString())
                    Log.e("success", user?.data?.user?.gender.toString())
                    Log.e("success", user?.data?.user?.birthDate.toString())
                    Toast.makeText(activity,"Register success", Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("Error" ,t.message.toString())
                Toast.makeText(activity,"Register Failed", Toast.LENGTH_LONG).show()

            }

        })
    }

    private fun showErrorMessage(editText: EditText, textView: TextView, errorMessage: String){
        editText.error = errorMessage
        textView.text = errorMessage
    }

    private fun showDatePicker(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // to show DatePickerDialoge
        birthDateEditText.setOnClickListener {
            val datePickerDialog = DatePickerDialog(it.context, { _, myYear, myMonth, myDay ->
                // set to editText
                birthDateEditText.setText("$myDay/$myMonth/$myYear")
            },year,month,day)
            // show dialog
            datePickerDialog.show()
        }
    }

    private fun customSpinner(){
        val gender = resources.getStringArray(R.array.gender)
        val arrayAdapter = ArrayAdapter(requireContext(),R.layout.dropdown_item,gender)
        genderFieldEditText.setAdapter(arrayAdapter)
    }

    private fun validatePhoneNumber(){
        phoneEditText.doAfterTextChanged {
            val errorMessage = "Phone Number is required"
            if (phoneEditText.text.toString().isEmpty()){
                showErrorMessage(phoneEditText,phoneErrorMessage,errorMessage)
            }else {
                phoneErrorMessage.text = ""
            }
        }
    }

    private fun validateCity(){
        cityEditText.doAfterTextChanged {
            val errorMessage = "City is required"
            if (cityEditText.text.toString().isEmpty()){
                showErrorMessage(cityEditText,cityErrorMessage,errorMessage)
            }else {
                cityErrorMessage.text = ""
            }
        }
    }

    private fun validateBirthDate(){
        birthDateEditText.doAfterTextChanged {
            val errorMessage = "Birth Date is required"
            if (birthDateEditText.text.toString().isEmpty()){
                showErrorMessage(birthDateEditText,birthDateErrorMessage,errorMessage)
            }else {
                birthDateErrorMessage.text = ""
            }
        }
    }

//    private fun validateGender(){
//        genderFieldEditText.doAfterTextChanged {
//            val errorMessage = "Gender is required"
//            if (genderFieldEditText.text.toString().isEmpty()){
//                showErrorMessage(genderFieldEditText,genderFieldErrorMessage,errorMessage)
//            }else {
//                genderFieldErrorMessage.text = ""
//            }
//        }
//    }

}