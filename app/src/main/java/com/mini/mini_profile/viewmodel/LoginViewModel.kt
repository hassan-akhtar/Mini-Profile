package com.mini.mini_profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mini.mini_profile.data.OperationCallback
import com.mini.mini_profile.model.auth.AuthenticationRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class LoginViewModel : ViewModel() , KoinComponent {
    private val repository: AuthenticationRepository by inject()
    private val _userAuthenticated = MutableLiveData<Any>()
    val userAuthenticated: LiveData<Any> = _userAuthenticated
    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    fun login(username: String, password: String) {
        GlobalScope.launch {
            repository.login(username, password, object : OperationCallback {
                override fun onError(obj: Any?) {
                    if (obj != null && obj is String) {
                        _onMessageError.postValue(obj.toString())
                    }
                }
                override fun onSuccess(obj: Any?) {
                    if (obj != null && obj is String) {
                        _userAuthenticated.postValue(obj.toString())

                    }
                }
            })
        }
    }

    fun validateData(username: String, password: String): Boolean {
        return when {
            username.trim().isEmpty() -> {
                _onMessageError.postValue("Please enter username")
                false
            }
            password.trim().isEmpty() -> {
                _onMessageError.postValue("Please enter password")
                false
            }
            else -> true
        }
    }

}