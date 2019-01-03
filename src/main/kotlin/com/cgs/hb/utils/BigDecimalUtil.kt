package com.cgs.hb.utils

import java.math.BigDecimal

class BigDecimalUtil {

    //加
    fun add(d1: Double, d2: Double): Double {
        var b1 = BigDecimal(d1)
        var b2 = BigDecimal(d2)
        return b1.add(b2).toDouble()
    }

    //减
    fun sub(d1: Double, d2: Double): Double {
        var b1 = BigDecimal(d1)
        var b2 = BigDecimal(d2)
        return b1.subtract(b2).toDouble()
    }

    //乘
    fun mul(d1: Double, d2: Double): Double {
        var b1 = BigDecimal(d1)
        var b2 = BigDecimal(d2)
        return b1.multiply(b2).toDouble()
    }

    //除
    fun div(d1: Double, d2: Double, len: Int): Double {
        var b1 = BigDecimal(d1)
        var b2 = BigDecimal(d2)
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    /**
     * 任何一个数字除以1都是原数字
     * ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
     */
    fun round(d: Double, len: Int): Double {
        var b1 = BigDecimal(d)
        var b2 = BigDecimal(1)
        return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).toDouble()
    }

    fun main(args: Array<String>) {
        println(div(0.1, 0.5, 3))
    }
}
