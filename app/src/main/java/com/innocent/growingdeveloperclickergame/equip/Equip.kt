package com.innocent.growingdeveloperclickergame.equip

class Equip(type: EquipType, price: Int, codingPowerRate: Int, resourceId: String) {
    val type: EquipType
    val price: Int
    val codingPowerRate: Int
    val resourceId: String

    init {
        this.type = type
        this.price = price
        this.codingPowerRate = codingPowerRate
        this.resourceId = resourceId
    }
}