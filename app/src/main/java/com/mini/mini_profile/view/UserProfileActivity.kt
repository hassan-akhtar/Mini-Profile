package com.mini.mini_profile.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mini.mini_profile.model.user.User
import com.mini.mini_profile.viewmodel.UserProfileViewModel
import kotlinx.android.synthetic.main.activity_user_profile.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import android.net.Uri
import android.view.View
import com.mini.mini_profile.R
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.FileNotFoundException
import java.io.InputStream


class UserProfileActivity : BaseActivity() {

    private val viewModel by viewModel<UserProfileViewModel>()
    private var encodedImage : String = ""
    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.userId = intent.getStringExtra(getString(R.string.key_user_id)).toString()
        viewModel.populateUserData.observe(this, populateUserDataObserver)
        viewModel.showMessage.observe(this, showMessageObserver)
        showLoader(true)
        viewModel.getUser()
    }

     fun updateUserDetails(view : View) {
         if (viewModel.validateData(etFirstName.text.toString(), etLastName.text.toString(), etEmail.text.toString())) {
             showLoader(true)
             viewModel.updateUser(etFirstName.text.toString(), etLastName.text.toString(), etEmail.text.toString(), encodedImage)
         }
    }

    fun logout(view : View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

     fun checkPermission(view : View) {
        //check runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE)
            }
            else{
                //permission already granted
                pickImageFromGallery()
            }
        }
        else{
            //system OS is < Marshmallow
            pickImageFromGallery()
        }
    }

    private val showMessageObserver = Observer<Any> {
        showLoader(false)
        showToast("$it")
    }

    private val populateUserDataObserver = Observer<User> {
        showLoader(false)
        etFirstName.setText(it.firstName)
        etLastName.setText(it.lastName)
        etEmail.setText(it.email)
        setImage(it.avatar)
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                   showToast(getString(R.string.msg_permission_denied))
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            ivAvatar.setImageURI(data?.data)
            encodedImage = data?.data?.let { encodeToBase64(it) }.toString()
        }
    }

    private fun encodeToBase64(uri: Uri): String {
        var imageStream: InputStream? = null
        try {
            imageStream = this.contentResolver.openInputStream(uri)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val image = BitmapFactory.decodeStream(imageStream)
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private fun setImage(imageString: String){
        try {
                val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                ivAvatar.setImageBitmap(decodedImage)
        } catch (e: Exception) {
            ivAvatar.setBackgroundResource(R.drawable.ic_avatar_placeholder)
        }
    }

}
