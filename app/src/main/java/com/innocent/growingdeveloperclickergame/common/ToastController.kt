package com.innocent.growingdeveloperclickergame.common

import android.app.Activity
import android.view.View
import android.widget.Toast

object ToastController {
    private var currentToast: Toast? = null

    fun showToast(activity: Activity, text: String) {
        if (currentToast?.view?.windowVisibility == View.VISIBLE) {
            currentToast!!.cancel()
        }
        currentToast = Toast.makeText(activity, text, Toast.LENGTH_SHORT)
        currentToast!!.show()
    }
}