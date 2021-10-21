package com.innocent.growingdeveloperclickergame.project

import android.util.Log
import com.innocent.growingdeveloperclickergame.equip.EquipDC
import com.innocent.growingdeveloperclickergame.main.*

object ProjectDC: CodingPowerListener {
    init {
        CodingPowerDC.addListener(this)
    }

    private val projects: Array<Project> = arrayOf(
        Project("졸업과제", 50, 500, 10000),
        Project("홈페이지", 10000, 1000, 30000),
        Project("쇼핑몰", 30000, 3000, 100000),
        Project("2D게임", 50000, 5000, 500000),
        Project("???", 100000, 10000, 1000000)
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

    fun getMinLimitCodingPower(projectIdx: Int): Int {
        val isValidIdx: Boolean = projectIdx >= 0 && projectIdx < projects.size
        if (!isValidIdx) return 0
        return  projects[projectIdx].minLimitCodingPower
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

    fun getLowestLimitCodingPower(): Int {
        val lowestProject = projects.minByOrNull { equip -> equip.minLimitCodingPower }
        if (lowestProject == null) return 0
        return lowestProject!!.minLimitCodingPower
    }
}

interface ProjectDCListener {
    fun onStartProject()
    fun onProgress(progressRate: Int)
    fun onCompleteProject(project: Project)
}