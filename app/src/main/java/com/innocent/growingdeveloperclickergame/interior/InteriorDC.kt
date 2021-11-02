package com.innocent.growingdeveloperclickergame.main

import android.util.Log
import com.innocent.growingdeveloperclickergame.equip.Equip
import com.innocent.growingdeveloperclickergame.equip.EquipDC
import com.innocent.growingdeveloperclickergame.equip.EquipListener
import com.innocent.growingdeveloperclickergame.equip.EquipType
import com.innocent.growingdeveloperclickergame.interior.Interior
import com.innocent.growingdeveloperclickergame.interior.InteriorType

object InteriorDC {
    private val dogs: Array<Interior> = arrayOf(
        Interior("강아지 Lv.1", InteriorType.DOG, 1, 100),
        Interior("강아지 Lv.2", InteriorType.DOG, 1000000000, 1000),
    )

    private val cats: Array<Interior> = arrayOf(
        Interior("고양이 Lv.1", InteriorType.CAT, 1, 100),
        Interior("고양이 Lv.2", InteriorType.CAT, 1000000000, 1000),
    )

    private val LISTENERS: ArrayList<InteriorListener> = ArrayList()
    private val interiors: MutableList<Interior> = mutableListOf()

    fun init(interiorIdxSet: Set<String>, interiorType: InteriorType) {
        for (i in interiors.size - 1 downTo 0) {
            if (interiors[i].type === interiorType) interiors.removeAt(i)
        }
        val interiorShop = getInteriors(interiorType)
        interiorIdxSet.forEach {
            val idx = it.toInt()
            if (idx < 0 || idx >= interiorShop.size)
                return
            val interior = interiorShop[idx]
            interiors.add(interior)
            onChangeInterior(interior)
        }
    }

    private fun getInteriors(interiorType: InteriorType): Array<Interior> {
        when(interiorType) {
            InteriorType.DOG -> return InteriorDC.dogs
            InteriorType.CAT -> return InteriorDC.cats
        }

        return emptyArray()
    }

    private fun onChangeInterior(interior: Interior) {
        LISTENERS.forEach { listener -> listener.onChangeInterior(interior)}
    }

    fun canBuyEquip(interiorIdx: Int, interiorType: InteriorType): Boolean {
        Log.d("InteriorDC", "canBuyEquip")
        val interiorShop = getInteriors(interiorType)
        val isValidIdx: Boolean = interiorIdx >= 0 && interiorIdx < interiorShop.size
        return isValidIdx && interiorShop[interiorIdx].price <= MoneyDC.getMoney()
    }

    fun buyInterior(interiorIdx: Int, interiorType: InteriorType): Interior? {
        Log.d("InteriorDC", "buyInterior")
        if (!canBuyEquip(interiorIdx, interiorType)) return null
        val interiorShop = getInteriors(interiorType)
        val interior = interiorShop[interiorIdx]
        interiors.add(interior)
        MoneyDC.minusMoney(interior.price)
        onChangeInterior(interior)
        return interior;
    }

    fun addListener(listener: InteriorListener) {
        LISTENERS.add(listener)
    }

    fun getInteriorAutoCodingPowerPerTime(): Int {
        var codingPowerPerTime = 0
        interiors.forEach{ codingPowerPerTime += it.codingPowerPerTime }
        return codingPowerPerTime
    }

    fun getInteriorIdxSet(interiorType: InteriorType): Set<String> {
        val equipShop = EquipDC.getEquipShop(interiorType)
        val equipIdxSet = HashSet<String>()
        EquipDC.equips.filter { equip -> equip.type === interiorType}.forEach { equip -> equipIdxSet.add((equipShop.indexOf(equip)).toString()) }
        return equipIdxSet;
    }

    fun getCheapestInteriorPrice(): Int {
        return arrayOf(
            EquipDC.keyboardShop.minOf { equip -> equip.price },
            EquipDC.chairShop.minOf { equip -> equip.price }
//            keyboardShop.minByOrNull { equip -> equip.price },
//            keyboardShop.minByOrNull { equip -> equip.price }
        ).minOf { price -> price }
    }

    fun getNextInteriorIdx(interiorType: InteriorType): Int {
        val equipShop = EquipDC.getEquipShop(equipType)
        return equipShop.indexOfFirst { equip -> !EquipDC.equips.contains(equip) }
    }

    fun getInterior(interiorIdx: Int, interiorType: InteriorType): Equip {
        return EquipDC.getEquipShop(interiorType)[equipIdx]
    }
}


interface InteriorListener {
    fun onChangeInterior(interior: Interior)
}