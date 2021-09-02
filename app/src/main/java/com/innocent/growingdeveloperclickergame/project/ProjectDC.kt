package com.innocent.growingdeveloperclickergame.project

import android.util.Log
import com.innocent.growingdeveloperclickergame.main.*

object ProjectDC: CounterDCListener {
    init {
        CounterDC.addListener(this)
    }

    private val projects: Array<Project> = arrayOf(Project(0, 14, 3000))
    private var countOfFinishProject: Int = 0
    private var currentProject: Project? = null

    private val listeners: ArrayList<ProjectDCListener> = ArrayList()

    fun addListener(listener: ProjectDCListener) {
        Log.d("ProjectDC", "addListener")
        listeners.add(listener)
    }

    fun startProject(projectIdx: Int) {
        Log.d("ProjectDC", "startProject")
        if (!canProject(projectIdx)) return

        val project = projects[projectIdx]
        currentProject = project
        countOfFinishProject = CounterDC.getCount() + project.countOfClick
        listeners.forEach { listener -> listener.onStartProject() }
    }

    private fun repeatProject() {
        Log.d("ProjectDC", "finishProject")
        if (currentProject === null) return

        val rewardMoney = currentProject!!.rewardMoney
        countOfFinishProject = CounterDC.getCount() + currentProject!!.countOfClick
        MoneyDC.plusMoney(rewardMoney)
    }

    fun canProject(projectIdx: Int): Boolean {
        Log.d("ProjectDC", "canProject")
        val isValidIdx: Boolean = projectIdx >= 0 && projectIdx < projects.size
        return isValidIdx && projects[projectIdx].minLimitCodingPower <= CodingPowerDC.getCodingPower()
    }

    override fun onClick(count: Int) {
        Log.d("ProjectDC", "onClick")
        if (currentProject === null) return
        val range = currentProject!!.countOfClick
        val start = countOfFinishProject - currentProject!!.countOfClick
        listeners.forEach { listener -> listener.onProgress((count - start) * 100 / range)}
        if (count >= countOfFinishProject) {
            repeatProject()
        }
    }
}

interface ProjectDCListener {
    fun onStartProject()
    fun onProgress(progressRate: Int)
}