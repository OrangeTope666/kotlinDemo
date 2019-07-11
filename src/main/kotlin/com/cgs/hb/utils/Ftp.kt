package com.cgs.hb.utils

import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPClientConfig
import org.apache.commons.net.ftp.FTPReply
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class Ftp {
    companion object {
        fun uploadFile(url: String, port: Int, userName: String, password: String, path: String, fileName: String, input: InputStream): Boolean {
            var flag = false
            var ftp = FTPClient()
            try {
                //连接ftp
                ftp.connect(url, port)
                FTPClientConfig(FTPClientConfig.SYST_NT)
                var reply = ftp.replyCode
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftp.disconnect()
                    return flag
                }
                ftp.changeWorkingDirectory(path)
                ftp.enterLocalPassiveMode()
                ftp.setFileType(FTP.BINARY_FILE_TYPE)
                ftp.storeFile(fileName, input)
                input.close()
                ftp.logout()
                flag = true
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (ftp.isConnected) {
                    ftp.disconnect()
                }
            }
            return flag
        }

        fun download(url: String, port: Int, userName: String, password: String, remotePath: String, fileName: String, localPath: String): Boolean {
            var flag = false
            var ftp = FTPClient()
            try {
                ftp.connect(url, port)
                ftp.login(userName, password)
                var reply = ftp.replyCode
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftp.disconnect()
                    return flag
                }
                ftp.changeWorkingDirectory(remotePath)
                var file = File(localPath)
                var os = FileOutputStream(file)
                ftp.enterLocalPassiveMode()
                ftp.retrieveFile(fileName, os)
                os.close()
                flag = true
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return flag
        }
    }
}