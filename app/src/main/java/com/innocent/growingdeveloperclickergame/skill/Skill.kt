package com.innocent.growingdeveloperclickergame.skill

enum class Skill(val cost: Long, val duration: Long, val displayName: String) {
    POWER_DOUBLE(50, 5000, "5초간 코딩력x2 (50원)"),
    POWER_TRIPLE(100, 5000, "5초간 코딩력x3 (100원)")
}