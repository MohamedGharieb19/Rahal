package com.example.rahal.ui.user

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.Navigation
import com.example.rahal.R
import com.example.rahal.databinding.FragmentVerificationCodeUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationCodeUserFragment : Fragment(), TextWatcher {
    lateinit var binding: FragmentVerificationCodeUserBinding
    private lateinit var backArrowButton: ImageView
    private lateinit var firstEditText: EditText
    private lateinit var secondEditText: EditText
    private lateinit var thirdEditText: EditText
    private lateinit var fourthEditText: EditText
    private lateinit var nextButton: Button
    companion object { const val NUM_OF_DIGITS = 4 }
    private var verificationCode = ""
    private val editTextArray: ArrayList<EditText> = ArrayList(NUM_OF_DIGITS)
    lateinit var numTemp:String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentVerificationCodeUserBinding.inflate(inflater,container,false)
        intilaizeVariables()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            addElementsToEditTextArray()
            addListnersToEditText()

        backArrowButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.forgetPasswordUserFragment)
        }

        nextButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.changePasswordUserFragment)
        }

    }

    private fun addElementsToEditTextArray(){
        editTextArray.add(0,firstEditText)
        editTextArray.add(1,secondEditText)
        editTextArray.add(2,thirdEditText)
        editTextArray.add(3,fourthEditText)
    }

    private fun addListnersToEditText(){
        editTextArray[0].addTextChangedListener(this)
        editTextArray[1].addTextChangedListener(this)
        editTextArray[2].addTextChangedListener(this)
        editTextArray[3].addTextChangedListener(this)

        editTextArray[0].requestFocus()
    }

    private fun intilaizeVariables(){
        backArrowButton = binding.backArrowButton
        firstEditText = binding.firstEditText
        secondEditText = binding.secondEditText
        thirdEditText = binding.thirdEditText
        fourthEditText = binding.fourthEditText
        nextButton = binding.nextButton
    }

    override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
         numTemp = s.toString()
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(s: Editable) {
        (0 until editTextArray.size)
            .forEach { i ->
                if (s === editTextArray[i].editableText) {

                    if (s.isBlank()) {
                        return
                    }
                    if (s.length >= 2) {//if more than 1 char
                        val newTemp = s.toString().substring(s.length - 1, s.length)
                        if (newTemp != numTemp) {
                            editTextArray[i].setText(newTemp)
                        } else {
                            editTextArray[i].setText(s.toString().substring(0, s.length - 1))
                        }
                    } else if (i != editTextArray.size - 1) { //not last char
                        editTextArray[i + 1].requestFocus()
                        editTextArray[i + 1].setSelection(editTextArray[i + 1].length())

                        return
                    } else

                    //will verify code the moment the last character is inserted and all digits have a number
                        verifyCode(testCodeValidity())
                    Log.e("code",verificationCode)

                }
            }


    }

    /** Set the edittext views to be editable / uneditable
     *
     */
    private fun enableCodeEditTexts(enable: Boolean) {
        for (i in 0 until editTextArray.size)
            editTextArray[i].isEnabled = enable
    }


    /** Initialize all views back to blanks and focus on first view
     *
     */
    private fun initCodeEditTexts() {
        for (i in 0 until editTextArray.size)
            editTextArray[i].setText("")
        editTextArray[0].requestFocus()
    }

    /** Use this function to set the views text from a string i.e using an sms listener to
     * read the code off an sms
     */
    private fun setVerificationCode(verificationCode: String) {
        for (i in 0 until editTextArray.size)
            editTextArray[i].setText(verificationCode.substring(i, i))
    }

    /** Returns the code if it has the correct number of digits, else ""
     *
     */
    private fun testCodeValidity(): String {

        for (i in editTextArray.indices) {
            val digit = editTextArray[i].text.toString().trim { it <= ' ' }
            verificationCode += digit
        }
        if (verificationCode.trim { it <= ' ' }.length == NUM_OF_DIGITS) {
            return verificationCode
        }
        return ""
    }

    /**Verify the code - you take it from here
     *
     */
    private fun verifyCode(verificationCode: String) {
        if (verificationCode.isNotEmpty()) {
            enableCodeEditTexts(false)
            //Your code
        }
    }



}