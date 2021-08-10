package com.innocent.growingdeveloperclickergame.project

class Project(limitCodingPerformance: Int, time: Int, reward: Int) {
    val limitCodingPerformance: Int
    val time: Int
    val reward: Int

    init {
        this.limitCodingPerformance = limitCodingPerformance
        this.time = time
        this.reward = reward
    }
}