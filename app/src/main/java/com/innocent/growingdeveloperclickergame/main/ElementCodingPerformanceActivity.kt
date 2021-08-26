package com.innocent.growingdeveloperclickergame.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.innocent.growingdeveloperclickergame.databinding.ActivityElementClickerBinding
import com.innocent.growingdeveloperclickergame.equip.EquipDC
import com.innocent.growingdeveloperclickergame.project.ProjectDC

class ElementCodingPerformanceActivity : AppCompatActivity(), CodingPerformanceListener, MoneyListener {
    private lateinit var binding: ActivityElementClickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElementClickerBinding.inflate(layoutInflater)

        // CodingPerformanceDC에 리스너 등록
        CodingPerformanceDC.addListener(this)
        // MoneyDC 리스너 등록
        MoneyDC.addListener(this)

        // Background 어디를 클릭하더라도 click이벤트 발생
        binding.clickerBackground.setOnTouchListener { v, event ->
            Log.d("Clicker", event.action.toString())
            val action = event.action
            if (action === MotionEvent.ACTION_DOWN) CodingPerformanceDC.click()
            else if (action and MotionEvent.ACTION_POINTER_DOWN === MotionEvent.ACTION_POINTER_DOWN) CodingPerformanceDC.click()
            return@setOnTouchListener true
        }
        binding.btnProject.setOnClickListener { ProjectDC.startProject(0) }
        binding.btnEquip.setOnClickListener { EquipDC.buyEquip(0) }
        setContentView(binding.root)
    }

    // 코딩력 변경될 때 핸들링
    override fun onChangeCodingPerformance(codingPerformance: Int) {
        Log.d("Activity", "onChangeCodingPerformance")
        binding.tvCodingPerformance.text = codingPerformance.toString() + "C"
    }

    // 돈 변경될 때 핸들링
    override fun onChangeMoney(money: Int) {
        Log.d("Activity", "onChangeMoney")
        binding.tvMoney.text = money.toString() + "\\"
    }
}