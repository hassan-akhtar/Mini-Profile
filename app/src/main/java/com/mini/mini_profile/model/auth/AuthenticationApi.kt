package com.mini.mini_profile.model.auth

import com.mini.mini_profile.data.OperationCallback

interface AuthenticationApi {
        suspend fun login(username : String, password : String, callback: OperationCallback)
}