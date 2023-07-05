package com.example.rahal.data

import com.google.gson.annotations.SerializedName

 class UserResponse{

	@field:SerializedName("data")
	val data: Data? = null

	@field:SerializedName("status")
	val status: String? = null

	@field:SerializedName("token")
	val token: String? = null


 class Data(

	@field:SerializedName("user")
	val user: User? = null
)

 class User(
	 @field:SerializedName("name")
	 var name: String? = null,

	 @field:SerializedName("email")
	 var email: String? = null,

	@field:SerializedName("password")
	var password: String? = null,

	 @field:SerializedName("passwordConfirm")
	 var verifyPassword: String? = null,

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	 @field:SerializedName("city")
	 val city: String? = null,

	 @field:SerializedName("phoneNumber")
	 val phoneNumber: String? = null,

	 @field:SerializedName("gender")
	 val gender: String? = null,

	 @field:SerializedName("birthDate")
	 val birthDate: String? = null,
)
 }