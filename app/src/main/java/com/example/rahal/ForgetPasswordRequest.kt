package com.example.rahal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ForgetPasswordRequest {

    @SerializedName("email")
    @Expose
    var email: String? = null
}