package com.innocent.growingdeveloperclickergame.project

import android.util.Log
import com.innocent.growingdeveloperclickergame.main.*

object ProjectDC: CodingPowerListener {
    init {
        CodingPowerDC.addListener(this)
    }

    private val projects: Array<Project> = arrayOf(
        Project("홈페이지", 50, 100, 50000),
        Project("쇼핑몰", 50, 200, 100000)
    )

    private var projectInProgress: Project? = null
    private var startCodingPower: Int = 0
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
        listener?.onStartProject()
        listener?.onProgress(0)
        return projectInProgress
    }

    private fun repeatProject() {
        Log.d("ProjectDC", "finishProject")
        if (projectInProgress === null) return

        val rewardMoney = projectInProgress!!.reward
        MoneyDC.plusMoney(rewardMoney)
    }

    fun hasProjectInProgress(): Boolean {
        return projectInProgress != null
    }

    fun canProject(projectIdx: Int): Boolean {
        Log.d("ProjectDC", "canProject")
        val isValidIdx: Boolean = projectIdx >= 0 && projectIdx < projects.size
        return isValidIdx && projects[projectIdx].minLimitCodingPower <= CodingPowerDC.getCodingPower()
    }

    fun getProjectIdx(): Int {
        return projects.indexOf(projectInProgress)
    }

    fun getProjectStartCodingPower(): Int {
        return if (projectInProgress != null) startCodingPower else 0
    }

    override fun onChangeCodingPower(codingPower: Int) {
        if (projectInProgress == null) return

        val project = projectInProgress!!

        val progressRate = (codingPower - startCodingPower) * 100 / project.cost
        listener?.onProgress(progressRate)

        if (progressRate < 100) return

        MoneyDC.plusMoney(project.reward)
        listener?.onCompleteProject(project)
        // 반복수행
        startProject(projects.indexOf(project))
    }
}

interface ProjectDCListener {
    fun onStartProject()
    fun onProgress(progressRate: Int)
    fun onCompleteProject(project: Project)
}