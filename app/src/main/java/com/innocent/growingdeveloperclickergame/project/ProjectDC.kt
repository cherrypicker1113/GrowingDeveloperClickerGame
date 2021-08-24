package com.innocent.growingdeveloperclickergame.project

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.innocent.growingdeveloperclickergame.main.CodingPowerDC
import com.innocent.growingdeveloperclickergame.main.MoneyDC

object ProjectDC {
    private val projects: Array<Project> = arrayOf(Project(10, 1000, 1000))
    private val looper = Handler(Looper.getMainLooper()) // setInterval같은 느낌으로 사용하기 위해 추가]

    fun startProject(projectIdx: Int) {
        Log.d("ProjectDC", "startProject")
        if (!canProject(projectIdx)) return

        val project = projects[projectIdx]
        looper.postDelayed(object : Runnable {
            override fun run() {
                MoneyDC.plusMoney(project.reward)
            }
        }, project.time.toLong())
    }

    fun canProject(projectIdx: Int): Boolean {
        Log.d("ProjectDC", "canProject")
        val isValidIdx: Boolean = projectIdx >= 0 && projectIdx < projects.size
        return isValidIdx && projects[projectIdx].limitCodingPower <= CodingPowerDC.getCodingPower()
    }
}