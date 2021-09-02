package com.innocent.growingdeveloperclickergame.main

import android.util.Log

// 싱글턴처럼 사용하기 위해 object키워드 사용
object CodingPowerDC: CounterDCListener {
    init {
        CounterDC.addListener(this)
    }

    private var codingPower: Int = 0
    private var codingPowerRate: Int = 1
    private val LISTENERS: ArrayList<CodingPowerListener> = ArrayList()

    fun getCodingPower(): Int {
        Log.d("CodingPowerDC", "getCodingPower")
        return codingPower
    }

    // 클릭시 코딩력 오르고 카운터 증가 (카운터는 미사용)
    // 코딩력 변경될 경우 리스너 리스트 돌면서 알려줌
    override fun onClick(count: Int) {
        Log.d("CodingPowerDC", "onClick")
        codingPower += codingPowerRate
        LISTENERS.forEach { listener -> listener.onChangeCodingPower(codingPower) }
    }

    fun addCodingPowerRate(rate: Int) {
        Log.d("CodingPowerDC", "addCodingPowerRate")
        codingPowerRate += rate
    }

    fun addListener(listener: CodingPowerListener) {
        Log.d("CodingPowerDC", "addListener")
        LISTENERS.add(listener)
    }
}

interface CodingPowerListener {
    fun onChangeCodingPower(codingPower: Int)
}