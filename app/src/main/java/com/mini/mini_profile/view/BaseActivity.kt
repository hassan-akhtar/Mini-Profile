package com.mini.mini_profile.view

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mini.mini_profile.R
import kotlinx.android.synthetic.main.activity_base.*

open class BaseActivity : AppCompatActivity() {

    private var progress: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        progress  = createProgressDialog(this)
    }

     fun showToast(msg : String){
        Toast.makeText(this,msg, Toast.LENGTH_LONG).show()
    }

    @SuppressLint("NewApi")
    fun createProgressDialog(context : Context): AlertDialog {
        val layoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.progress_dialogue_fragment, null)
        view.findViewById<ProgressBar>(R.id.progressBar).indeterminateDrawable.setColorFilter(
            getColor(
                R.color.color_white
            ), android.graphics.PorterDuff.Mode.SRC_IN
        )
        return AlertDialog.Builder(context).run {
            setView(view)
            setCancelable(false)
            create()
        }.apply {
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            requestWindowFeature(Window.FEATURE_NO_TITLE)
        }
    }

     fun showLoader(isVisible: Boolean) {
        if (isVisible) progress?.show() else progress?.dismiss()
    }
}
