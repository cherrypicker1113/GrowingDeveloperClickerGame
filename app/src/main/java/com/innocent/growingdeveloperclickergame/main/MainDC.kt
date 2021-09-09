package com.innocent.growingdeveloperclickergame.main

import android.app.Activity
import android.content.Context
import com.innocent.growingdeveloperclickergame.common.ToastController
import com.innocent.growingdeveloperclickergame.equip.EquipDC
import com.innocent.growingdeveloperclickergame.project.ProjectDC

enum class KEY {
    CODING_POWER, MONEY, PROJECT_IDX, PROJECT_START_CODING_POWER, EQUIP_IDXES
}

object MainDC {

    val USER_DATA_KEY: String = "USER_DATA"

    fun saveData(activity: Activity) {
        val sharedPref = activity.getSharedPreferences(USER_DATA_KEY, Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
//            Log.d(KEY.CODING_POWER.name, CodingPowerDC.getCodingPower().toString())
//            Log.d(KEY.MONEY.name, MoneyDC.getMoney().toString())
//            Log.d(KEY.PROJECT_IDX.name, ProjectDC.getProjectIdx().toString())
//            Log.d(KEY.PROJECT_START_CODING_POWER.name, ProjectDC.getProjectStartCodingPower().toString())
//            Log.d(KEY.EQUIP_IDXES.name, EquipDC.getEquipIdxSet().toString())

            putInt(KEY.CODING_POWER.name, CodingPowerDC.getCodingPower())
            putInt(KEY.MONEY.name, MoneyDC.getMoney())
            putInt(KEY.PROJECT_IDX.name, ProjectDC.getProjectIdx())
            putInt(KEY.PROJECT_START_CODING_POWER.name, ProjectDC.getProjectStartCodingPower())
            putStringSet(KEY.EQUIP_IDXES.name, EquipDC.getEquipIdxSet())
            commit()
        }
    }

    fun fetchData(activity: Activity) {
        val sharedPref = activity.getSharedPreferences(USER_DATA_KEY, Context.MODE_PRIVATE)
        CodingPowerDC.init(sharedPref.getInt(KEY.CODING_POWER.name, 0))
        MoneyDC.init(sharedPref.getInt(KEY.MONEY.name, 0))
        ProjectDC.init(
            sharedPref.getInt(KEY.PROJECT_IDX.name, -1),
            sharedPref.getInt(KEY.PROJECT_START_CODING_POWER.name, 0)
        )
        EquipDC.init(sharedPref.getStringSet(KEY.EQUIP_IDXES.name, HashSet())!!)
    }

    fun initData(activity: Activity) {
        CodingPowerDC.init(0)
        MoneyDC.init(0)
        ProjectDC.init(-1, 0)
        EquipDC.init(HashSet())

        saveData(activity)

        ToastController.showToast(activity, "데이터 초기화")
    }
}