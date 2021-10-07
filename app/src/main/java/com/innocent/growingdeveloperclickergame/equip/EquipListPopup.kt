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

        val nextKeyboardIdx = EquipDC.getNextEquipIdx(EquipType.KEYBOARD)
        val nextKeyboard = EquipDC.getEquip(EquipType.KEYBOARD, nextKeyboardIdx)
        binding.tvKeyboardTitle.text = nextKeyboard.name
        binding.tvKeyboardDetail.text = "클릭당 코딩력 + ${nextKeyboard.codingPowerRate}"
        binding.tvKeyboardPrice.text = "${nextKeyboard.price}\\"
        binding.btnBuyKeyboard.setOnClickListener { buyEquip(nextKeyboardIdx, EquipType.KEYBOARD) }


        val nextChairIdx = EquipDC.getNextEquipIdx(EquipType.CHAIR)
        val nextChair = EquipDC.getEquip(EquipType.CHAIR, nextChairIdx)
        binding.tvChairTitle.text = nextChair.name
        binding.tvChairDetail.text = "클릭당 코딩력 + ${nextChair.codingPowerRate}"
        binding.tvChairPrice.text = "${nextChair.price}\\"
        binding.btnBuyChair.setOnClickListener { buyEquip(nextChairIdx, EquipType.CHAIR) }

        val nextDeskIdx = EquipDC.getNextEquipIdx(EquipType.TABLE)
        val nextDest = EquipDC.getEquip(EquipType.TABLE, nextDeskIdx)
        binding.tvDeskTitle.text = nextDest.name
        binding.tvDeskDetail.text = "클릭당 코딩력 + ${nextDest.codingPowerRate}"
        binding.tvDeskPrice.text = "${nextDest.price}\\"
        binding.btnBuyDesk.setOnClickListener { buyEquip(nextDeskIdx, EquipType.TABLE) }

        val nextMonitorIdx = EquipDC.getNextEquipIdx(EquipType.MONITOR)
        val nextMonitor = EquipDC.getEquip(EquipType.MONITOR, nextMonitorIdx)
        binding.tvMonitorTitle.text = nextMonitor.name
        binding.tvMonitorDetail.text = "클릭당 코딩력 + ${nextMonitor.codingPowerRate}"
        binding.tvMonitorPrice.text = "${nextMonitor.price}\\"
        binding.btnBuyMonitor.setOnClickListener { buyEquip(nextMonitorIdx, EquipType.MONITOR) }

        val nextInteriorIdx = EquipDC.getNextEquipIdx(EquipType.INTERIOR)
        val nextInterior = EquipDC.getEquip(EquipType.INTERIOR, nextInteriorIdx)
        binding.tvInteriorTitle.text = nextInterior.name
        binding.tvInteriorDetail.text = "클릭당 코딩력 + ${nextInterior.codingPowerRate}"
        binding.tvInteriorPrice.text = "${nextInterior.price}\\"
        binding.btnBuyInterior.setOnClickListener { buyEquip(nextInteriorIdx, EquipType.INTERIOR) }
    }

    private fun buyEquip(equipIdx: Int, equipType: EquipType) {
        if (EquipDC.hasEquip(equipIdx, equipType)) {
            ToastController.showToast(activity, "이미 구매한 아이템입니다.")
            return
        }
        if (!EquipDC.canBuyEquip(equipIdx, equipType)) {
            ToastController.showToast(activity, "자산이 부족합니다.")
            return
        }
        val equip = EquipDC.buyEquip(equipIdx, equipType)
        if (equip != null) ToastController.showToast(activity, equip.name + " 를 구매했습니다.")
        close()
    }

    private fun close() {
        popupWindow!!.dismiss()
    }
}