package com.cgs.hb.entity

import jp.sourceforge.qrcode.data.QRCodeImage
import java.awt.image.BufferedImage

class MyQRCodeImage(internal var bufImg: BufferedImage) : QRCodeImage {

    fun MyQRCodeImage(bugImg: BufferedImage) {
        this.bufImg = bufImg
    }

    override fun getHeight(): Int {
        return bufImg.height
    }

    override fun getPixel(p0: Int, p1: Int): Int {
        return bufImg.getRGB(p0, p1)
    }

    override fun getWidth(): Int {
        return bufImg.width
    }

}