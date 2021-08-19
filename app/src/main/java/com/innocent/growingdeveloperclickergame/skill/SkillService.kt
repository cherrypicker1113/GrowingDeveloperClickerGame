package com.innocent.growingdeveloperclickergame.skill

import com.innocent.growingdeveloperclickergame.player.Player
import kotlin.concurrent.thread

object SkillService {
    fun getSkills(player: Player): Array<Skill> {
        return Skill.values()
    }

    fun useSkill(player: Player, skill: Skill) {
        if (!player.useSkill(skill))
            return
        thread(start = true) {
            Thread.sleep(skill.duration)
            player.finishSkill(skill)
        }
    }
}