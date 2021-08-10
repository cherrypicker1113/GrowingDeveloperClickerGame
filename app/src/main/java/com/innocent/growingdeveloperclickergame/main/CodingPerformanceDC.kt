package com.innocent.growingdeveloperclickergame.main

import android.util.Log

// 싱글턴처럼 사용하기 위해 object키워드 사용
object CodingPerformanceDC {
    private var codingPerformance: Int = 0
    private var codingPerformanceRate: Int = 1
    private var counter: Int = 0 // hello world 보여주는 기능때문에 추가 아직 미사용중
    private val listeners: ArrayList<CodingPerformanceListener> = ArrayList()

    fun getCodingPerformance(): Int {
        Log.d("CodingPerformanceDC", "getCodingPerformance")
        return codingPerformance
    }

    // 클릭시 코딩력 오르고 카운터 증가 (카운터는 미사용)
    // 코딩력 변경될 경우 리스너 리스트 돌면서 알려줌
    fun click() {
        Log.d("CodingPerformanceDC", "click")
        counter++
        codingPerformance += codingPerformanceRate
        listeners.forEach { listener -> listener.onChangeCodingPerformance(codingPerformance) }
    }

    fun addCodingPerformanceRate(rate: Int) {
        Log.d("CodingPerformanceDC", "addCodingPerformanceRate")
        codingPerformanceRate += rate
    }

    fun addListener(listener: CodingPerformanceListener) {
        Log.d("CodingPerformanceDC", "addListener")
        listeners.add(listener)
    }
}

interface CodingPerformanceListener {
    fun onChangeCodingPerformance(codingPerfomance: Int)
}