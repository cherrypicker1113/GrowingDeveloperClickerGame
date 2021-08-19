package com.innocent.growingdeveloperclickergame.common

import android.app.Activity
import android.content.Context
import com.innocent.growingdeveloperclickergame.player.Player

object SharedPreferencesService {
    private const val PLAYER_POWER_KEY: String = "PLAYER_POWER"
    private const val PLAYER_ASSET_KEY: String = "PLAYER_ASSET"
    private var player: Player? = null

    fun savePlayer(activity: Activity, player: Player) {
        putLong(activity, PLAYER_POWER_KEY, player.power)
        putLong(activity, PLAYER_ASSET_KEY, player.asset)
    }

    fun fetchPlayer(activity: Activity): Player {
        if (player == null) {
            val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
            val power = sharedPref.getLong(PLAYER_POWER_KEY, 0)
            val asset = sharedPref.getLong(PLAYER_ASSET_KEY, 0)
            player = Player(power, asset)
        }
        return player as Player
    }

    private fun putLong(activity: Activity, key: String, value: Long) {
        val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putLong(key, value)
            commit()
        }
    }
}