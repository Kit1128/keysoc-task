package com.example.keysoccodingtask.activity

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.example.keysoccodingtask.R

abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity() {

    protected lateinit var binding: VB
    protected var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateLayout(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        initLoadingDialog()
    }

    private fun initLoadingDialog() {
        loadingDialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.progress_dialog)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
        }
    }

    protected fun startLoading() {
        if (!isFinishing && !loadingDialog?.isShowing!!) {
            loadingDialog?.show()
        }
    }

    protected fun stopLoading() {
        if (!isFinishing && loadingDialog?.isShowing!!) {
            loadingDialog?.dismiss()
        }
    }

    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB
}