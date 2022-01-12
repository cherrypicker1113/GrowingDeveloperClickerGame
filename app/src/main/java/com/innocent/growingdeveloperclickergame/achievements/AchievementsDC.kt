package com.innocent.growingdeveloperclickergame.equip

import android.util.Log
import com.innocent.growingdeveloperclickergame.R
import com.innocent.growingdeveloperclickergame.main.MoneyDC

object AchievementsDC {
    private val keyboardShop: Array<Equip> = arrayOf(
        Equip("키보드 Lv.1", EquipType.KEYBOARD, 10000, 1, R.drawable.keyboard),
        Equip("키보드 Lv.2", EquipType.KEYBOARD, 30000, 1, R.drawable.keyboard),
        Equip("키보드 Lv.3", EquipType.KEYBOARD, 50000, 1, R.drawable.keyboard),
        Equip("키보드 Lv.4", EquipType.KEYBOARD, 130000, 3, R.drawable.keyboard),
        Equip("키보드 Lv.5", EquipType.KEYBOARD, 500000, 3, R.drawable.keyboard),
        Equip("키보드 Lv.6", EquipType.KEYBOARD, 800000, 3, R.drawable.keyboard),
        Equip("키보드 Lv.7", EquipType.KEYBOARD, 2000000, 5, R.drawable.keyboard),
        Equip("키보드 Lv.8", EquipType.KEYBOARD, 10000000, 10, R.drawable.keyboard),
        Equip("키보드 Lv.9", EquipType.KEYBOARD, 1000000000, 100, R.drawable.keyboard)
    )

    private val chairShop: Array<Equip> = arrayOf(
        Equip("의자 Lv.1", EquipType.CHAIR, 5000, 1, R.drawable.chair_2),
        Equip("의자 Lv.2", EquipType.CHAIR, 30000, 1, R.drawable.chair_3),
        Equip("의자 Lv.3", EquipType.CHAIR, 100000, 2, R.drawable.chair_4),
        Equip("의자 Lv.4", EquipType.CHAIR, 300000, 2, R.drawable.chair_5),
        Equip("의자 Lv.5", EquipType.CHAIR, 800000, 3, R.drawable.chair_6),
        Equip("의자 Lv.6", EquipType.CHAIR, 1000000000, 10, R.drawable.chair_6)
    )

    private val tableShop: Array<Equip> = arrayOf(
        Equip("책상 Lv.1", EquipType.TABLE, 50000, 1, R.drawable.desk_2),
        Equip("책상 Lv.2", EquipType.TABLE, 150000, 1, R.drawable.desk_3),
        Equip("책상 Lv.3", EquipType.TABLE, 700000, 2, R.drawable.desk_4),
        Equip("책상 Lv.4", EquipType.TABLE, 1000000, 3, R.drawable.desk_5),
        Equip("책상 Lv.5", EquipType.TABLE, 30000000, 5, R.drawable.desk_6),
        Equip("책상 Lv.6", EquipType.TABLE, 1000000000, 10, R.drawable.desk_6)
    )

    private val monitorShop: Array<Equip> = arrayOf(
        Equip("모니터 Lv.1", EquipType.MONITOR, 30000, 1, R.drawable.monitor_2),
        Equip("모니터 Lv.2", EquipType.MONITOR, 100000, 1, R.drawable.monitor_3),
        Equip("모니터 Lv.3", EquipType.MONITOR, 300000, 2, R.drawable.monitor_4),
        Equip("모니터 Lv.4", EquipType.MONITOR, 800000, 3, R.drawable.monitor_5),
        Equip("모니터 Lv.5", EquipType.MONITOR, 3000000, 3, R.drawable.monitor_6),
        Equip("모니터 Lv.6", EquipType.MONITOR, 1000000000, 100, R.drawable.monitor_6)
    )

    private val interiorShop: Array<Equip> = arrayOf(
        Equip("벽지 Lv.1", EquipType.INTERIOR, 100000, 3, R.drawable.background_2),
        Equip("벽지 Lv.2", EquipType.INTERIOR, 1000000, 3, R.drawable.background_3),
        Equip("벽지 Lv.3", EquipType.INTERIOR, 3000000, 3, R.drawable.background_4),
        Equip("벽지 Lv.4", EquipType.INTERIOR, 10000000, 3, R.drawable.background_5),
        Equip("벽지 Lv.5", EquipType.INTERIOR, 1000000000, 10, R.drawable.background_5)
    )

//    private val equipsShop: Array<Equip> = arrayOf(
//        Equip("키보드", EquipType.KEYBOARD, 100000, 3, R.drawable.keyboard),
//        Equip("의자", EquipType.CHAIR, 300000, 6, R.drawable.chair_2),
//        Equip("책상", EquipType.TABLE, 500000, 10, R.drawable.desk2),
//        Equip("모니터", EquipType.MONITER, 10000000, 20, R.drawable.moniter2)
//    )
    private val LISTENERS: ArrayList<EquipListener> = ArrayList()
    private val equips: MutableList<Equip> = mutableListOf()

    fun init(equipIdxSet: Set<String>, equipType: EquipType) {
        for (i in equips.size - 1 downTo 0) {
            if (equips[i].type === equipType) equips.removeAt(i)
        }
        val equipShop = getEquipShop(equipType)
        equipIdxSet.forEach {
            val idx = it.toInt()
            if (idx < 0 || idx >= equipShop.size)
                return
            val equip = equipShop[idx]
            equips.add(equip)
            onChangeEquip(equip)
        }
    }

    private fun getEquipShop(equipType: EquipType): Array<Equip> {
        when(equipType) {
            EquipType.KEYBOARD -> return keyboardShop
            EquipType.CHAIR -> return chairShop
            EquipType.TABLE -> return tableShop
            EquipType.MONITOR -> return monitorShop
            EquipType.INTERIOR -> return interiorShop
        }

        return emptyArray()
    }

    fun buyEquip(equipIdx: Int, equipType: EquipType): Equip? {
        Log.d("EquipDC", "buyEquip")
        if (!canBuyEquip(equipIdx, equipType)) return null
        val equipShop = getEquipShop(equipType)
        val equip = equipShop[equipIdx]
        equips.add(equip)
        MoneyDC.minusMoney(equip.price)
        onChangeEquip(equip)
        return equip;
    }

    private fun onChangeEquip(equip: Equip) {
        LISTENERS.forEach { listener -> listener.onChangeEquip(equip)}
    }

    fun hasAnyEquip(): Boolean {
        return equips.size > 0
    }
    fun hasEquip(equipIdx: Int, equipType: EquipType): Boolean {
        val equipShop = getEquipShop(equipType)
        return equips.contains(equipShop[equipIdx])
    }

    fun canBuyEquip(equipIdx: Int, equipType: EquipType): Boolean {
        Log.d("EquipDC", "canBuyEquip")
        val equipShop = getEquipShop(equipType)
        val isValidIdx: Boolean = equipIdx >= 0 && equipIdx < equipShop.size
        return isValidIdx && equipShop[equipIdx].price <= MoneyDC.getMoney()
    }

    fun addListener(listener: EquipListener) {
        Log.d("EquipDC", "addListener")
        LISTENERS.add(listener)
    }

    fun getEquipCodingPowerRate(): Int {
        var codingRate = 0
        equips.forEach{ codingRate += it.codingPowerRate }
        return codingRate
    }

    fun getEquipIdxSet(equipType: EquipType): Set<String> {
        val equipShop = getEquipShop(equipType)
        val equipIdxSet = HashSet<String>()
        equips.filter {equip -> equip.type === equipType}.forEach { equip -> equipIdxSet.add((equipShop.indexOf(equip)).toString()) }
        return equipIdxSet;
    }

    fun getCheapestEquipPrice(): Int {
        return arrayOf(
            keyboardShop.minOf { equip -> equip.price },
            chairShop.minOf { equip -> equip.price }
//            keyboardShop.minByOrNull { equip -> equip.price },
//            keyboardShop.minByOrNull { equip -> equip.price }
        ).minOf { price -> price }
    }

    fun getNextEquipIdx(equipType: EquipType): Int {
        val equipShop = getEquipShop(equipType)
        return equipShop.indexOfFirst { equip -> !equips.contains(equip) }
    }

    fun getEquip(equipType: EquipType, equipIdx: Int): Equip {
        return getEquipShop(equipType)[equipIdx]
    }
}