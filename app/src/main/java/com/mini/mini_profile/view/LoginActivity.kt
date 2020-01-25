package com.mini.mini_profile.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mini.mini_profile.R
import com.mini.mini_profile.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent


class LoginActivity : BaseActivity() {

    private  val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.userAuthenticated.observe(this,userAuthenticatedObserver)
        viewModel.onMessageError.observe(this,onMessageErrorObserver)
    }

     fun login(view : View) {
        if (viewModel.validateData(etUsername.text.toString(), etPassword.text.toString())) {
            showLoader(true)
            viewModel.login(etUsername.text.toString(), etPassword.text.toString())        }
    }

    private val userAuthenticatedObserver= Observer<Any> {
        showLoader(false)
        showToast(getString(R.string.msg_login_success))
        val intent = Intent(this, UserProfileActivity::class.java)
        intent.putExtra(getString(R.string.key_user_id), it.toString())
        startActivity(intent)
        finish()
    }

    private val onMessageErrorObserver= Observer<Any> {
        showLoader(false)
        showToast("$it")
    }
}


