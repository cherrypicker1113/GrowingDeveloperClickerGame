package com.innocent.growingdeveloperclickergame.equip

import android.util.Log
import com.innocent.growingdeveloperclickergame.R
import com.innocent.growingdeveloperclickergame.main.MoneyDC

object EquipDC {
    private val equipsShop: Array<Equip> = arrayOf(
        Equip("키보드", EquipType.KEYBOARD, 100000, 3, R.drawable.moniter2),
        Equip("의자", EquipType.CHAIR, 300000, 3, R.drawable.chair2),
        Equip("책상", EquipType.TABLE, 1000, 3, R.drawable.desk2),
        Equip("모니터", EquipType.MONITER, 1000, 3, R.drawable.moniter2)
    )
    private val LISTENERS: ArrayList<EquipListener> = ArrayList()
    private val equips: MutableList<Equip> = mutableListOf()

    fun init(equipIdxSet: Set<String>) {
        equips.clear()
        equipIdxSet.forEach {
            val idx = it.toInt()
            if (idx < 0 || idx >= equipsShop.size)
                return
            val equip = equipsShop[idx]
            equips.add(equip)
            onChangeEquip(equip)
        }
    }

    fun buyEquip(equipIdx: Int): Equip? {
        Log.d("EquipDC", "buyEquip")
        if (!canBuyEquip(equipIdx)) return null
        val equip = equipsShop[equipIdx]
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
    fun hasEquip(equipIdx: Int): Boolean {
        return equips.contains(equipsShop[equipIdx])
    }

    fun canBuyEquip(equipIdx: Int): Boolean {
        Log.d("EquipDC", "canBuyEquip")
        val isValidIdx: Boolean = equipIdx >= 0 && equipIdx < equipsShop.size
        return isValidIdx && equipsShop[equipIdx].price <= MoneyDC.getMoney()
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

    fun getEquipIdxSet(): Set<String> {
        val equipIdxSet = HashSet<String>()
        for (equip in equips) {
            equipIdxSet.add((equipsShop.indexOf(equip)).toString())
        }
        return equipIdxSet;
    }

    fun getCheapestEquipPrice(): Int {
        val cheapestEquip = equipsShop.minByOrNull { equip -> equip.price }
        if (cheapestEquip == null) return 0
        return cheapestEquip!!.price
    }
}

interface EquipListener {
    fun onChangeEquip(equip: Equip)
}