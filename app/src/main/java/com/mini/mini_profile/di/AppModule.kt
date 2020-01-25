package com.mini.mini_profile.di

import com.google.firebase.auth.FirebaseAuth
import com.mini.mini_profile.model.auth.AuthenticationRepository
import com.mini.mini_profile.model.user.UserRepository
import com.mini.mini_profile.viewmodel.LoginViewModel
import com.mini.mini_profile.viewmodel.UserProfileViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule= module {
    single { AuthenticationRepository() }
    single { UserRepository }
    single { FirebaseAuth.getInstance()}
    viewModel {LoginViewModel()}
    viewModel {UserProfileViewModel()}
}