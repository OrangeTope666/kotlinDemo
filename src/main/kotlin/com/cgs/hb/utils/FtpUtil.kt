package com.cgs.hb.utils

import org.apache.commons.lang3.StringUtils
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import org.springframework.web.multipart.MultipartFile
import java.io.*
import java.util.*

class FtpUtil {

    internal var ftp = FTP()
    var ftpClient: FTPClient? = null

    companion object {
        var hostName = "127.0.0.1"
        var port = 21
        var userName = "ftp"
        var password = "1234"
        var remoteFtpDir = "d:\\fto\\"
        var uploadImg = ""
        var downloadDir = "D:\\JAVA\\download\\"
        var encode = "gbk"

        init {
            var props = Properties()
            props.load(FtpUtil::class.java.classLoader.getResourceAsStream("ftp.properties"))
            val hostname = props.getProperty("hostname")
            val port = props.getProperty("port")
            val username = props.getProperty("username")
            val password = props.getProperty("password")
            val remoteFtpDir = props.getProperty("remoteFtpDir")
            val uploadImg = props.getProperty("uploadImg")

            FtpUtil.hostName = hostname
            FtpUtil.port = Integer.parseInt(port)
            FtpUtil.userName = username
            FtpUtil.password = password
            FtpUtil.remoteFtpDir = remoteFtpDir
            FtpUtil.uploadImg = uploadImg
        }
    }

    fun initFtpClient() {
        ftpClient = FTPClient()
        ftpClient!!.controlEncoding = encode
        try {
            ftpClient!!.connect(hostName, port)
            ftpClient!!.login(userName, password)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //ftp上传文件
    fun uploadImg(fileName: String, file: MultipartFile) {
        try {
            Ftp.uploadFile(hostName, port, userName, password, uploadImg, fileName, file.inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun uploadFile(saveRelativePath: String, fileName: String, originFileName: String) {
        var input: InputStream? = null
        try {
            input = FileInputStream(File(originFileName))
            initFtpClient()
            ftpClient!!.setFileType(2)
            ftpClient!!.makeDirectory(remoteFtpDir)
            ftpClient!!.changeWorkingDirectory(remoteFtpDir)
            var name = ""
            if (StringUtils.isNotBlank(saveRelativePath)) {
                var path = remoteFtpDir + saveRelativePath
                FileOrFileDirUtil.createDir(path)
                name = saveRelativePath + "\\" + fileName
            }
            ftpClient!!.storeFile(name,input)
            ftpClient!!.logout()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (ftpClient!!.isConnected) ftpClient!!.disconnect()
            input!!.close()
        }
    }
    fun downloadFile(path: String, fileName: String, localFile: String) {
        var os: OutputStream? = null
        try{
            initFtpClient()
            if (StringUtils.isNotBlank(path)) {
                ftpClient!!.changeWorkingDirectory(path + "\\")
            } else {
                ftpClient!!.changeWorkingDirectory(remoteFtpDir)
            }
            var ftpFiles = ftpClient!!.listFiles()
            for (file in ftpFiles) {
                if (fileName!!.equals(file.name)) {
                    var local = File(localFile)
                    os = FileOutputStream(local)
                    ftpClient!!.retrieveFile(file.name, os)
                }
            }
            ftpClient!!.logout()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (ftpClient!!.isConnected) ftpClient!!.disconnect()
            os!!.close()
        }
    }

    fun delteFile(path: String, fileName: String): Boolean {
        var flag = false
        try {
            initFtpClient()
            if (StringUtils.isNotBlank(path)) {
                ftpClient!!.changeWorkingDirectory(path + "\\")
            } else{
                ftpClient!!.changeWorkingDirectory(remoteFtpDir)
            }
            ftpClient!!.dele(fileName)
            ftpClient!!.logout()
            flag = true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (ftpClient!!.isConnected) ftpClient!!.disconnect()
        }
        return flag
    }
}