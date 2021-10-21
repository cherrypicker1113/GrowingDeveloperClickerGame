package com.innocent.growingdeveloperclickergame.common

import android.content.Context
import android.view.View
import android.widget.Toast

object ToastController {
    private var currentToast: Toast? = null

    fun showToast(context: Context, text: String) {
        currentToast?.cancel()
        currentToast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
        currentToast!!.show()
    }
}