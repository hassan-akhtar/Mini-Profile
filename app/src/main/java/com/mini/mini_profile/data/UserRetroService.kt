package com.mini.mini_profile.data

import com.mini.mini_profile.model.user.User
import com.mini.mini_profile.model.user.UserRepository
import retrofit2.Call
import retrofit2.http.*

interface UserRetroService {

    // Update user
    @PUT(UserRepository.URL_UPDATE_USER)
     fun updateUser(@Body user : User): Call<GetUserResponse>

    // Get user
    @GET(UserRepository.URL_GET_USER)
     fun getUser(@Query("userId") userId: String): Call<GetUserResponse>
}