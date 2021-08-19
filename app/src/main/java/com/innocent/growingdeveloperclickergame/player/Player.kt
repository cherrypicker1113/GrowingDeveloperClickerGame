package com.innocent.growingdeveloperclickergame.player

import com.innocent.growingdeveloperclickergame.item.Item
import com.innocent.growingdeveloperclickergame.skill.Skill

class Player(var power: Long, var asset: Long) {

    private val skills: MutableList<Skill> = mutableListOf()
    private val items: MutableList<Item> = mutableListOf()

    fun increasePower(): Player {
        power += getPowerPerClick()
        return this
    }

    fun addAsset(_asset: Long): Player {
        asset += _asset
        return this
    }

    private fun getPowerPerClick(): Long {
        var powerPerClick: Long = 1
        skills.forEach { powerPerClick = getPowerPerClickBySkill(powerPerClick, it) }
        return powerPerClick;
    }

    fun useSkill(skill: Skill): Boolean {
        if (asset < skill.cost)
            return false
        asset -= skill.cost
        skills.add(skill)
        return true
    }

    fun finishSkill(skill: Skill) {
        skills.remove(skill)
    }

    private fun getPowerPerClickBySkill(powerPerClick: Long, skill: Skill): Long {
        return when (skill) {
            Skill.POWER_DOUBLE -> powerPerClick * 2
            Skill.POWER_TRIPLE -> powerPerClick * 3
            else -> powerPerClick
        }
    }
}