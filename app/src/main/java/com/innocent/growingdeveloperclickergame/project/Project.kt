package com.innocent.growingdeveloperclickergame.project

class Project(minLimitCodingPower: Int, countOfClick: Int, rewardMoney: Int) {
    val minLimitCodingPower: Int
    val countOfClick: Int
    val rewardMoney: Int

    init {
        this.minLimitCodingPower = minLimitCodingPower
        this.countOfClick = countOfClick
        this.rewardMoney = rewardMoney
    }
}