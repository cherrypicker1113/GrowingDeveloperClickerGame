package com.innocent.growingdeveloperclickergame.equip

import android.util.Log
import com.innocent.growingdeveloperclickergame.main.CodingPowerDC
import com.innocent.growingdeveloperclickergame.main.MoneyDC

object EquipDC {
    private val equips: Array<Equip> = arrayOf(Equip(2000, 9))

    fun buyEquip(equipIdx: Int) {
        Log.d("EquipDC", "buyEquip")
        if (!canBuyEquip(equipIdx)) return

        val equip = equips[equipIdx]
        CodingPowerDC.addCodingPowerRate(equip.codingPowerRate)
        MoneyDC.minusMoney(equip.price)
    }

    fun canBuyEquip(equipIdx: Int): Boolean {
        Log.d("EquipDC", "canBuyEquip")
        val isValidIdx: Boolean = equipIdx >= 0 && equipIdx < equips.size
        return isValidIdx && equips[equipIdx].price <= MoneyDC.getMoney()
    }
}