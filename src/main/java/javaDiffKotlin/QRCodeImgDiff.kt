package javaDiffKotlin

import jp.sourceforge.qrcode.data.QRCodeImage

import java.awt.image.BufferedImage

/**
 * @author chengz
 * @date 2019/1/4 17:43
 */
class QRCodeImgDiff(internal var bufferedImage: BufferedImage) : QRCodeImage {

    override fun getWidth(): Int {
        // TODO Auto-generated method stub
        return bufferedImage.width
    }

    // 像素还是颜色
    override fun getPixel(arg0: Int, arg1: Int): Int {
        // TODO Auto-generated method stub
        return bufferedImage.getRGB(arg0, arg1)
    }

    override fun getHeight(): Int {
        // TODO Auto-generated method stub
        return bufferedImage.height
    }

}
