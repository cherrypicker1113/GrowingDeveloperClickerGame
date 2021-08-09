package com.innocent.growingdeveloperclickergame.main

import android.os.Handler
import android.os.Looper

// 싱글턴처럼 사용하기 위해 object키워드 사용
object ClickerCounterDC {
    var codingPerformance: Int = 0
    var money: Int = 0
    var counter: Int = 0 // hello world 보여주는 기능때문에 추가 아직 미사용중
    val listeners: ArrayList<ClickerCounterListener> = ArrayList()
    val looper = Handler(Looper.getMainLooper()) // setInterval같은 느낌으로 사용하기 위해 추가

    // 클릭시 코딩력 오르고 카운터 증가 (카운터는 미사용)
    // 코딩력 변경될 경우 리스너 리스트 돌면서 알려줌
    public fun click() {
        counter++
        codingPerformance++
        listeners.forEach { listener -> listener.onChangeCodingPerformance(codingPerformance) }
    }

    public fun addListener(listener: ClickerCounterListener) {
        listeners.add(listener)

        // 리스너 등록 이후부터 동작 (2초마다 돈을 1원씩 올려줌) (테스트용)
        looper.post(object : Runnable {
            override fun run() {
                money++
                listeners.forEach { listener -> listener.onChangeMoney(money) }
                looper.postDelayed(this, 2000)
            }
        })
    }
}

interface ClickerCounterListener {
    fun onChangeCodingPerformance(codingPerfomance: Int)
    fun onChangeMoney(money: Int)
}