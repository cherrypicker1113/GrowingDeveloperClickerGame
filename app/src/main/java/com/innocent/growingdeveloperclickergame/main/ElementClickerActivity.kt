package com.innocent.growingdeveloperclickergame.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.innocent.growingdeveloperclickergame.R
import com.innocent.growingdeveloperclickergame.databinding.ActivityElementClickerBinding
import com.innocent.growingdeveloperclickergame.equip.Equip
import com.innocent.growingdeveloperclickergame.equip.EquipDC
import com.innocent.growingdeveloperclickergame.equip.EquipListener
import com.innocent.growingdeveloperclickergame.equip.EquipType
import com.innocent.growingdeveloperclickergame.project.ProjectDC

class ElementClickerActivity : AppCompatActivity(), CodingPowerListener, MoneyListener, EquipListener {
    private lateinit var binding: ActivityElementClickerBinding
    private var currentEquipIdx: Int = 0 //일단 이미지 바뀌는지 테스트용으로 여기에 추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElementClickerBinding.inflate(layoutInflater)

        // CodingPerformanceDC에 리스너 등록
        CodingPowerDC.addListener(this)
        // MoneyDC 리스너 등록
        MoneyDC.addListener(this)
        // EquipDC 리스너 등록
        EquipDC.addListener(this)

        // Background 어디를 클릭하더라도 click이벤트 발생
        binding.clickerBackground.setOnTouchListener { v, event ->
            Log.d("Clicker", event.action.toString())
            val action = event.action
            if (action === MotionEvent.ACTION_DOWN) CodingPowerDC.click()
            else if (action and MotionEvent.ACTION_POINTER_DOWN === MotionEvent.ACTION_POINTER_DOWN) CodingPowerDC.click()
            return@setOnTouchListener true
        }
        binding.btnProject.setOnClickListener { ProjectDC.startProject(0) }
        binding.btnEquip.setOnClickListener {
            if (EquipDC.canBuyEquip(currentEquipIdx)) {
                EquipDC.buyEquip(currentEquipIdx)
                currentEquipIdx++
            }
        }
        setContentView(binding.root)
    }

    // 코딩력 변경될 때 핸들링
    override fun onChangeCodingPower(codingPerformance: Int) {
        Log.d("Activity", "onChangeCodingPerformance")
        runOnUiThread { binding.tvCodingPower.text = codingPerformance.toString() + "C" }
    }

    override fun onClick() {
        Log.d("Activity", "onClick")
        runOnUiThread {
            binding.playerRight.visibility = if (binding.playerRight.visibility === View.VISIBLE) View.INVISIBLE else View.VISIBLE
            binding.playerLeft.visibility = if (binding.playerLeft.visibility === View.VISIBLE) View.INVISIBLE else View.VISIBLE
        }
    }

    // 돈 변경될 때 핸들링
    override fun onChangeMoney(money: Int) {
        Log.d("Activity", "onChangeMoney")
        runOnUiThread { binding.tvMoney.text = money.toString() + "\\" }
    }

    override fun onChangeEquip(equip: Equip) {
        Log.d("Activity", "onChangeEquip")
        when(equip.type) {
            EquipType.TABLE -> binding.imgDesk.background = ContextCompat.getDrawable(this, equip.resourceId)
            EquipType.CHAIR -> binding.imgChair.background = ContextCompat.getDrawable(this, equip.resourceId)
            EquipType.MONITER -> binding.imgMoniter.background = ContextCompat.getDrawable(this, equip.resourceId)
        }
    }
}