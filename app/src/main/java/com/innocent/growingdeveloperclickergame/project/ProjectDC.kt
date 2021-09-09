package com.innocent.growingdeveloperclickergame.project

import android.util.Log
import com.innocent.growingdeveloperclickergame.main.*

object ProjectDC {
    private val projects: Array<Project> = arrayOf(
        Project("홈페이지", 50, 100, 50000),
        Project("쇼핑몰", 50, 200, 100000)
    )

    private var projectInProgress: Project? = null
    private var startCodingPower: Int = 0
    private var countOfFinishProject: Int = 0
    private var currentProject: Project? = null
    private var listener: ProjectDCListener? = null

    fun setListener(listener: ProjectDCListener) {
        this.listener = listener
    }

    fun init(projectIdx: Int, startCodingPower: Int) {
        if (projectIdx < 0 || projectIdx >= projects.size)
            return
        projectInProgress = projects[projectIdx]
        this.startCodingPower = startCodingPower
    }

    fun startProject(projectIdx: Int): Project? {
        Log.d("ProjectDC", "startProject")
        if (!canProject(projectIdx)) return null

        projectInProgress = projects[projectIdx]
        startCodingPower = CodingPowerDC.getCodingPower()
        return projectInProgress
    }

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
    fun hasProjectInProgress(): Boolean {
        return projectInProgress != null
    }

    fun canProject(projectIdx: Int): Boolean {
        Log.d("ProjectDC", "canProject")
        val isValidIdx: Boolean = projectIdx >= 0 && projectIdx < projects.size
        return isValidIdx && projects[projectIdx].minLimitCodingPower <= CodingPowerDC.getCodingPower()
    }

    fun checkProjectInProgress(codingPower: Int) {
        if (projectInProgress == null || ((codingPower - startCodingPower) < projectInProgress!!.cost))
            return
        MoneyDC.plusMoney(projectInProgress!!.reward)
        listener?.onCompleteProject(projectInProgress!!)
        projectInProgress = null
    }

    fun getProjectIdx(): Int {
        return projects.indexOf(projectInProgress)
    }

    fun getProjectStartCodingPower(): Int {
        return if (projectInProgress != null) startCodingPower else 0
    }
}

interface ProjectDCListener {
    fun onStartProject()
    fun onProgress(progressRate: Int)
    fun onCompleteProject(project: Project)
}