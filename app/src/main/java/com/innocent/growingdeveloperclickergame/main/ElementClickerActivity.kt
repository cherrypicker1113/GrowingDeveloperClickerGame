package com.innocent.growingdeveloperclickergame.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.innocent.growingdeveloperclickergame.databinding.ActivityElementClickerBinding

class ElementClickerActivity : AppCompatActivity(), ClickerCounterListener {
    private lateinit var binding: ActivityElementClickerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElementClickerBinding.inflate(layoutInflater)

        // ClickerCounterDC에 리스너 등록
        ClickerCounterDC.addListener(this)

        // Background 어디를 클릭하더라도 click이벤트 발생
        binding.clickerBackground.setOnClickListener { ClickerCounterDC.click() }
        setContentView(binding.root)
    }

    // 코딩력 변경될 때 핸들링
    override fun onChangeCodingPerformance(codingPerformance: Int) {
        Log.d("codingPerformance", codingPerformance.toString())
        binding.tvCodingPerformance.text = codingPerformance.toString()
    }

    // 돈 변경될 때 핸들링
    override fun onChangeMoney(money: Int) {
        Log.d("money", money.toString())
        binding.tvMoney.text = money.toString()
    }
}