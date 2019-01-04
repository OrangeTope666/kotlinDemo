package javaDiffKotlin

import com.cgs.hb.utils.Ftp
import org.apache.commons.net.ftp.FTPClient

import java.io.IOException
import java.util.Properties

/**
 * @author chengz
 * @date 2019/1/4 14:59
 */
class StaticDiff {
    internal var ftp = Ftp()
    var ftpClient: FTPClient? = null

    companion object {
        // 可以从配置文件读取：
        // ftp服务器地址
        var hostname = "127.0.0.1xx"// "127.0.0.1";
        // ftp服务器端口号默认为21
        var port: Int? = 21
        // ftp登录账号
        var username = "ftpxx"
        // ftp登录密码
        var password = "ftpxx"
        var remoteFtpDir = "D:\\ftpxx\\" // ftp目录：目前没用到 folder5\\
        // 检测要点附件存放目录
        var uploadImg = ""
        var downloadDir = "D:\\JAVA\\download\\"

        var Encode = "gbk" // gbk utf-8 不编码 会乱码：

        init {
            val props = Properties()
            try {
                props.load(StaticDiff::class.java.classLoader.getResourceAsStream("ftp.propertiesdb"))
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val hostname = props.getProperty("hostname")
            val port = props.getProperty("port")
            val username = props.getProperty("username")
            val password = props.getProperty("password")
            val remoteFtpDir = props.getProperty("remoteFtpDir")
            val uploadImg = props.getProperty("uploadImg")

            StaticDiff.hostname = hostname
            StaticDiff.port = Integer.parseInt(port)
            StaticDiff.username = username
            StaticDiff.password = password
            StaticDiff.remoteFtpDir = remoteFtpDir
            StaticDiff.uploadImg = uploadImg
        }
    }
}
