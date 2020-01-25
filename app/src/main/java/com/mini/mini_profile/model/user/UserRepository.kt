package com.mini.mini_profile.model.user

import com.mini.mini_profile.data.ApiClient
import com.mini.mini_profile.data.GetUserResponse
import com.mini.mini_profile.data.OperationCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository: UserApi {

    private var call: Call<GetUserResponse>?=null
    const val URL_GET_USER = "/getUser"
    const val URL_UPDATE_USER = "/updateUser"

    override suspend fun updateUser(user: User, callback: OperationCallback) {
        call = ApiClient.build()?.updateUser(user)
        call?.enqueue(object : Callback<GetUserResponse> {
            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<GetUserResponse>,
                response: Response<GetUserResponse>
            ) {
                response?.body()?.let {
                    if (response.isSuccessful) {
                        callback.onSuccess(it.message)
                    } else {
                        callback.onError("Something went wrong")
                    }
                }
            }
        })
    }

    override suspend fun getUser(userId: String, callback: OperationCallback) {
        call = ApiClient.build()?.getUser(userId)
        call?.enqueue(object : Callback<GetUserResponse> {
            override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<GetUserResponse>,
                response: Response<GetUserResponse>
            ) {
                response?.body()?.let {
                    if (response.isSuccessful) {
                        callback.onSuccess(it.user)
                    } else {
                        callback.onError("Something went wrong")
                    }
                }
            }
        })
    }

}