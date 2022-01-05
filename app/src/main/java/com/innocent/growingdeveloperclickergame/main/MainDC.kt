package com.innocent.growingdeveloperclickergame.main

import android.app.Activity
import android.content.Context
import com.innocent.growingdeveloperclickergame.common.ToastController
import com.innocent.growingdeveloperclickergame.equip.EquipDC
import com.innocent.growingdeveloperclickergame.equip.EquipType
import com.innocent.growingdeveloperclickergame.project.ProjectDC

enum class KEY {
    CODING_POWER, MONEY, PROJECT_IDX, PROJECT_START_CODING_POWER, KEYBOARD_IDXES, DESK_IDXES, MONITOR_IDXES, INTERIOR_IDXES, CHAIR_IDXES
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
            putStringSet(KEY.KEYBOARD_IDXES.name, EquipDC.getEquipIdxSet(EquipType.KEYBOARD))
            putStringSet(KEY.CHAIR_IDXES.name, EquipDC.getEquipIdxSet(EquipType.CHAIR))
            putStringSet(KEY.DESK_IDXES.name, EquipDC.getEquipIdxSet(EquipType.TABLE))
            putStringSet(KEY.MONITOR_IDXES.name, EquipDC.getEquipIdxSet(EquipType.MONITOR))
            putStringSet(KEY.INTERIOR_IDXES.name, EquipDC.getEquipIdxSet(EquipType.INTERIOR))
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
        EquipDC.init(sharedPref.getStringSet(KEY.KEYBOARD_IDXES.name, HashSet())!!, EquipType.KEYBOARD)
        EquipDC.init(sharedPref.getStringSet(KEY.CHAIR_IDXES.name, HashSet())!!, EquipType.CHAIR)
        EquipDC.init(sharedPref.getStringSet(KEY.DESK_IDXES.name, HashSet())!!, EquipType.TABLE)
        EquipDC.init(sharedPref.getStringSet(KEY.MONITOR_IDXES.name, HashSet())!!, EquipType.MONITOR)
        EquipDC.init(sharedPref.getStringSet(KEY.INTERIOR_IDXES.name, HashSet())!!, EquipType.INTERIOR)
    }

    fun existData(): Boolean {
        return CodingPowerDC.getCodingPower() > 0 || MoneyDC.getMoney() > 0
    }

    fun initData(activity: Activity) {
        CodingPowerDC.init(0)
        MoneyDC.init(0)
        ProjectDC.init(-1, 0)
        EquipDC.init(HashSet(), EquipType.KEYBOARD)
        EquipDC.init(HashSet(), EquipType.CHAIR)
        EquipDC.init(HashSet(), EquipType.TABLE)
        EquipDC.init(HashSet(), EquipType.MONITOR)
        EquipDC.init(HashSet(), EquipType.INTERIOR)

        saveData(activity)

        ToastController.showToast(activity, "데이터 초기화")
    }
}