package com.innocent.growingdeveloperclickergame.main

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.innocent.growingdeveloperclickergame.common.ToastController
import com.innocent.growingdeveloperclickergame.databinding.ActivityElementClickerBinding
import com.innocent.growingdeveloperclickergame.equip.*
import com.innocent.growingdeveloperclickergame.project.Project
import com.innocent.growingdeveloperclickergame.project.ProjectDC
import com.innocent.growingdeveloperclickergame.project.ProjectDCListener
import com.innocent.growingdeveloperclickergame.project.ProjectListPopup


class ElementClickerActivity : AppCompatActivity(), CodingPowerListener, MoneyListener, EquipListener, CounterDCListener, ProjectDCListener {
    init {
        // CodingPerformanceDC에 리스너 등록
        CodingPowerDC.addListener(this)
        // MoneyDC 리스너 등록
        MoneyDC.addListener(this)
        // EquipDC 리스너 등록
        EquipDC.addListener(this)
        // CounterDC 리스너 등록
        CounterDC.addListener(this)
        ProjectDC.setListener(this)
    }
    private lateinit var binding: ActivityElementClickerBinding
    private var currentEquipIdx: Int = 0 //일단 이미지 바뀌는지 테스트용으로 여기에 추가
    private val helloWorld: String = "Hello, World!"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElementClickerBinding.inflate(layoutInflater)

        MainDC.fetchData(this)

        // Background 어디를 클릭하더라도 click이벤트 발생
        binding.clickerBackground.setOnTouchListener { v, event ->
            val action = event.action
            if (action === MotionEvent.ACTION_DOWN
                || (action and MotionEvent.ACTION_POINTER_DOWN) === MotionEvent.ACTION_POINTER_DOWN) CounterDC.click()
            MainDC.saveData(this)
            return@setOnTouchListener true
        }
        binding.btnProject.setOnClickListener { ProjectListPopup(this).show() }
        binding.btnEquip.setOnClickListener { EquipListPopup(this).show() }
        setContentView(binding.root)
    }

    // 코딩력 변경될 때 핸들링
    override fun onChangeCodingPower(codingPerformance: Int) {
        Log.d("Activity", "onChangeCodingPerformance")
        runOnUiThread { binding.tvCodingPower.text = codingPerformance.toString() + "C" }
    }

    override fun onClick(count: Int) {
        Log.d("Activity", "onClick")
        runOnUiThread {
            binding.playerRight.visibility = if (binding.playerRight.visibility === View.VISIBLE) View.INVISIBLE else View.VISIBLE
            binding.playerLeft.visibility = if (binding.playerLeft.visibility === View.VISIBLE) View.INVISIBLE else View.VISIBLE
            binding.tvHelloWorld.text = helloWorld.substring(0, count % (helloWorld.length + 1))
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

    override fun onCompleteProject(project: Project) {
        ToastController.showToast(this, project.name + " 프로젝트를 완료했습니다.")
    }
}