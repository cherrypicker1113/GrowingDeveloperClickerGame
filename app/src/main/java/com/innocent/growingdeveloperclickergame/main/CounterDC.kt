package com.innocent.growingdeveloperclickergame.main

import android.util.Log

object CounterDC {
    private var count: Int = 0
    private val listeners: ArrayList<CounterDCListener> = ArrayList()

    fun getCount(): Int {
        return count
    }

    fun click() {
        Log.d("CounterDC", "click")
        count++
        listeners.forEach{ listener -> listener.onClick(count) }
    }

    fun addListener(listener: CounterDCListener) {
        Log.d("CounterDC", "addListener")
        listeners.add(listener)
    }
}

interface CounterDCListener {
    fun onClick(count: Int)
}