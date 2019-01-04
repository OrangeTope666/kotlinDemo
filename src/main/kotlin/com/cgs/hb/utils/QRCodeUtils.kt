package com.cgs.hb.utils

import com.cgs.hb.entity.MyQRCodeImage
import com.swetake.util.Qrcode
import jp.sourceforge.qrcode.QRCodeDecoder
import sun.misc.BASE64Encoder
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import javax.imageio.ImageIO

class QRCodeUtils {
    companion object {


        fun encoderQRCode(content: String, imgPath: String) {
            this.encoderQRCode(content, imgPath, "png", 7)
        }

        fun encoderQRCode(content: String, ot: OutputStream) {
            this.encoderQRCode(content, ot, "png", 7)
        }

        fun encoderQRCode(content: String, imgPath: String, imgType: String) {
            this.encoderQRCode(content, imgPath, imgType, 7)
        }

        fun encoderQRCode(content: String, ot: OutputStream, imgType: String) {
            this.encoderQRCode(content, ot, imgType)
        }

        fun encoderQRCode(content: String, imgPath: String, imgType: String, size: Int) {
            try {
                var bugImg = this.qrCodeCommon(content, imgType, size)
                var imgFile = File(imgPath)
                //生成二维码图片
                ImageIO.write(bugImg, imgType, imgFile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun encoderQRCode(content: String, ot: OutputStream, imgType: String, size: Int) {
            try {
                var bufImg = this.qrCodeCommon(content, imgType, size)
                ImageIO.write(bufImg, imgType, ot)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun encoderWRCode(content: String, imgType: String, size: Int): String {
            var png_base64 = ""
            try {
                var bufImg = this.qrCodeCommon(content, imgType, size)
                var bos = ByteArrayOutputStream()
                ImageIO.write(bufImg, imgType, bos)
                var bytes = bos.toByteArray()
                var encoder = BASE64Encoder()
                png_base64 = encoder.encodeBuffer(bytes).trim()
                png_base64 = png_base64.replace(Regex("\n"), "").replace(Regex("\r"), "")

            } catch (e: Exception) {
                e.printStackTrace()
            }
            return png_base64
        }

        /**
         * 生成二维码(QRCode)图片的公共方法
         *
         * @param content
         *            存储内容
         * @param imgType
         *            图片类型
         * @param size
         *            二维码尺寸
         * @return
         */
        fun qrCodeCommon(content: String, imgType: String, size: Int): BufferedImage {
            var bufImg: BufferedImage? = null
            try {
                var qrcodeHandler = Qrcode()
                // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
                qrcodeHandler.qrcodeErrorCorrect = 'M'
                // 注意版本信息 N代表数字 、A代表 a-z,A-Z、B代表 其他)
                qrcodeHandler.qrcodeEncodeMode = 'B'
                // 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
                qrcodeHandler.qrcodeVersion = size
                // 获得内容的字节数组，设置编码格式
                var contentBytes = content.toByteArray(charset("UTF-8"))
                // 图片尺寸
                var imgSize = 67 + 12 * (size - 1)
                bufImg = BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB)
                var gs = bufImg.createGraphics()
                // 设置背景颜色
                gs.background = Color.WHITE
                gs.clearRect(0, 0, imgSize, imgSize)
                // 设定图像颜色> BLACK
                gs.color = Color.BLACK
                // 设置偏移量，不设置可能导致解析出错
                var pixoff = 2
                // 输出内容> 二维码
                if (contentBytes.size > 0 && contentBytes.size < 800) {
                    var codeOut = qrcodeHandler.calQrcode(contentBytes)
                    for (i in 0 until codeOut.size) {
                        for (j in 0 until codeOut.size) {
                            if (codeOut[j][i]) {
                                gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3)
                            }
                        }
                    }
                } else {
                    throw Exception("QRCode content bytes length = " + contentBytes.size + " not in [0, 800].")
                }
                gs.dispose()
                bufImg.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return bufImg!!
        }

        /**
         * 二维码解析
         */
        fun decoderQRCode(imgPath: String): String {
            var imgFile = File(imgPath)
            var content = ""
            try {
                var bufferedImage = ImageIO.read(imgFile)
                var decoder = QRCodeDecoder()
                content = String(decoder.decode(MyQRCodeImage(bufferedImage)), charset("UTF-8"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return content
        }

        fun decoderQRCode(intput: InputStream): String {
            var content = ""
            try {
                var bufImg = ImageIO.read(intput)
                var decoder = QRCodeDecoder()
                content = String(decoder.decode(MyQRCodeImage(bufImg)))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return content
        }
    }

}
fun main(args: Array<String>) {
   // QRCodeUtils.encoderQRCode("123234245", "E:\\a.png")
   println( QRCodeUtils.decoderQRCode("e:\\a.png"))
}