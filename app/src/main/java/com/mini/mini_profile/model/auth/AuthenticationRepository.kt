package com.mini.mini_profile.model.auth

import com.google.firebase.auth.FirebaseAuth
import com.mini.mini_profile.data.OperationCallback
import org.koin.core.KoinComponent
import org.koin.core.inject

class AuthenticationRepository : AuthenticationApi, KoinComponent {

    private val auth: FirebaseAuth by inject()

    override suspend fun login(username: String, password: String, callback: OperationCallback) {
        auth.signInWithEmailAndPassword(username, password).addOnCompleteListener {
            if(it.isSuccessful) callback.onSuccess(it.result?.user?.uid)
            else callback.onError("Incorrect username or password")
        }
    }
}