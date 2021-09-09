package com.innocent.growingdeveloperclickergame.equip

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.innocent.growingdeveloperclickergame.common.ToastController
import com.innocent.growingdeveloperclickergame.databinding.EquipListPopupBinding

class EquipListPopup(private val activity: Activity) {
    private lateinit var binding: EquipListPopupBinding
    private var popupWindow: PopupWindow? = null

    fun show() {
        binding = EquipListPopupBinding.inflate(activity.layoutInflater)
        val popupView: View = binding.root

        popupWindow = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        popupWindow!!.isFocusable = true
        popupWindow!!.setBackgroundDrawable(null)
        popupWindow!!.showAtLocation(popupView, Gravity.TOP, 0, 0)

        binding.close.setOnClickListener { close() }
        binding.keyboard.setOnClickListener { buyEquip(0) }
        binding.chair.setOnClickListener { buyEquip(1) }
    }

    private fun buyEquip(equipIdx: Int) {
        if (EquipDC.hasEquip(equipIdx)) {
            ToastController.showToast(activity, "이미 구매한 아이템입니다.")
            return
        }
        if (!EquipDC.canBuyEquip(equipIdx)) {
            ToastController.showToast(activity, "자산이 부족합니다.")
            return
        }
        val equip = EquipDC.buyEquip(equipIdx)
        if (equip != null) ToastController.showToast(activity, equip.name + " 를 구매했습니다.")
        close()
    }

    private fun close() {
        popupWindow!!.dismiss()
    }
}