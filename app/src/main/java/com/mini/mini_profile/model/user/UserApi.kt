package com.mini.mini_profile.model.user

import com.mini.mini_profile.data.OperationCallback

interface UserApi {
    suspend fun updateUser(user : User, callback: OperationCallback)
    suspend fun getUser(userId: String, callback: OperationCallback)
}