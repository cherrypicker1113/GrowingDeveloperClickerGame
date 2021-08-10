package com.innocent.growingdeveloperclickergame.main

import android.util.Log

object MoneyDC {
    private var money: Int = 0
    private val listeners: ArrayList<MoneyListener> = ArrayList()

    fun addListener(listener: MoneyListener) {
        Log.d("MoneyDC", "addListener")
        listeners.add(listener)
    }

    fun getMoney(): Int {
        Log.d("MoneyDC", "getMoney")
        return money
    }

    fun plusMoney(amount: Int) {
        Log.d("MoneyDC", "plusMoney")
        money += amount
        listeners.forEach{ listener -> listener.onChangeMoney(money) }
    }

    fun minusMoney(amount: Int) {
        Log.d("MoneyDC", "minusMoney")
        money -= amount
        listeners.forEach{ listener -> listener.onChangeMoney(money) }
    }
}

interface MoneyListener {
    fun onChangeMoney(money: Int)
}