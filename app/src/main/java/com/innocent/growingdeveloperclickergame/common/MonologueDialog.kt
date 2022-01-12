package com.innocent.growingdeveloperclickergame.common

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.innocent.growingdeveloperclickergame.databinding.DialogMonologueBinding

class MonologueDialog(private val activity: Activity) {
    private lateinit var binding: DialogMonologueBinding

    private var popupWindow: PopupWindow? = null

    fun showDialog(messages: ArrayList<String>) {
        binding = DialogMonologueBinding.inflate(activity.layoutInflater)
        val popupView: View = binding.root

        popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        popupWindow!!.isFocusable = true
//        popupWindow!!.setBackgroundDrawable(null)
        popupWindow!!.showAtLocation(popupView, Gravity.TOP, 0, 0)

        var idx = 0
        binding.tvMessage.text = messages[idx]

        // 확인 버튼
        binding.btnConfirm.setOnClickListener {
            popupWindow!!.dismiss()
        }

        // 다음 버튼
        binding.btnNext.setOnClickListener {
            idx ++
            binding.tvMessage.text = messages[idx] + "index = ${idx}, msgCt = ${messages.count()}"
            if (idx >= messages.count() - 1) {
                binding.btnNext.visibility = View.GONE
                binding.btnConfirm.visibility = View.VISIBLE
            }
        }

    }
}