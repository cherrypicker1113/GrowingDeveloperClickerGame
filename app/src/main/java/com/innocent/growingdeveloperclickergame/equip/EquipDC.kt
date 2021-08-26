package com.innocent.growingdeveloperclickergame.equip

import android.util.Log
import com.innocent.growingdeveloperclickergame.main.CodingPowerDC
import com.innocent.growingdeveloperclickergame.main.MoneyDC

object EquipDC {
    private val equipsShop: Array<Equip> = arrayOf(
        Equip(EquipType.CHAIR, 1000, 3, "@drawable/chair2"),
        Equip(EquipType.TABLE, 1000, 3, "@drawable/desk2"),
        Equip(EquipType.MONITER, 1000, 3, "@drawable/monitor2")
    )

    fun buyEquip(equipIdx: Int) {
        Log.d("EquipDC", "buyEquip")
        if (!canBuyEquip(equipIdx)) return

        val equip = equipsShop[equipIdx]
        CodingPowerDC.addCodingPowerRate(equip.codingPowerRate)
        MoneyDC.minusMoney(equip.price)
    }

    fun canBuyEquip(equipIdx: Int): Boolean {
        Log.d("EquipDC", "canBuyEquip")
        val isValidIdx: Boolean = equipIdx >= 0 && equipIdx < equipsShop.size
        return isValidIdx && equipsShop[equipIdx].price <= MoneyDC.getMoney()
    }
}