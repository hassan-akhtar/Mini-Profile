package com.mini.mini_profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mini.mini_profile.model.user.User
import com.mini.mini_profile.data.OperationCallback
import com.mini.mini_profile.model.user.UserRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class UserProfileViewModel : ViewModel(), KoinComponent {

    var userId: String = ""
    private val repository: UserRepository by inject()
    private val _populateUserData = MutableLiveData<User>()
    val populateUserData: LiveData<User> = _populateUserData
    private val _showMessage = MutableLiveData<Any>()
    val showMessage: LiveData<Any> = _showMessage

    fun getUser() {
        GlobalScope.launch {
            repository.getUser(userId, object : OperationCallback {
                override fun onError(obj: Any?) {
                    if (obj != null && obj is String) {
                        _showMessage.postValue(obj.toString())
                    }
                }
                override fun onSuccess(obj: Any?) {
                    if (obj != null && obj is User) {
                        _populateUserData.postValue(obj)
                    }
                }
            })
        }
    }

    fun updateUser(firstName: String, lastName: String, email: String, avatar: String) {
        GlobalScope.launch {
            repository.updateUser(
                User(userId, firstName, lastName, email, avatar),
                object : OperationCallback {
                    override fun onError(obj: Any?) {
                        if (obj != null && obj is String) {
                            _showMessage.postValue(obj.toString())
                        }
                    }

                    override fun onSuccess(obj: Any?) {
                        if (obj != null && obj is String) {
                            _showMessage.postValue(obj.toString())
                        }
                    }
                })
        }
    }

    fun validateData(firstName: String, lastName: String, email: String): Boolean {
        return when {
            firstName.trim().isEmpty() -> {
                _showMessage.postValue("Please enter first name")
                false
            }
            lastName.trim().isEmpty() -> {
                _showMessage.postValue("Please enter last name")
                false
            }
            email.trim().isEmpty() -> {
                _showMessage.postValue("Please enter your email")
                false
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _showMessage.postValue("Please enter valid email")
                false
            }
            else -> true
        }
    }
}