package com.innocent.growingdeveloperclickergame.project

class Project(limitCodingPower: Int, time: Int, reward: Int) {
    val limitCodingPower: Int
    val time: Int
    val reward: Int

    init {
        this.limitCodingPower = limitCodingPower
        this.time = time
        this.reward = reward
    }
}