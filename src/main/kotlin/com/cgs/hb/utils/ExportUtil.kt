package com.cgs.hb.utils

import org.apache.commons.lang3.StringUtils
import java.io.File
import java.io.FileInputStream
import javax.servlet.http.HttpServletResponse

class ExportUtil {

    fun commonDownload(response: HttpServletResponse, file: File, head: String) {
        response.reset()

        var map = Dom4jUtil.getMimeTypeMap()
        var hzm = file.name.split("\\.")[1]
        var cType: String = map.get(hzm) as String
        if (StringUtils.isBlank(cType) || "null".equals(cType, true)
                || "undefined".equals(cType, true) || "状态空".equals(cType)) {
            cType = "multipart/form-data"
        }
        response.setContentType(cType)

        var fileName = String(head.toByteArray(), charset("ISO-8859-1"))
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName)

        var inputStream = FileInputStream(file)
        var os = response.outputStream
        var bytesNumber = 0
        var buffer = ByteArray(4096)
        inputStream.use { input ->
            os.use {
                while ({ bytesNumber = input.read(buffer);bytesNumber }() != -1) {
                    it.write(bytesNumber)
                }
            }
        }
        os.close()
        inputStream.close()
    }

    fun commonDownload(response: HttpServletResponse, file: File, head: String, type: String) {
        response.reset()

        // 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        var contentType = "multipart/form-data"
        if ("excel".equals(type)) {
            contentType = "application/vnd.ms-excel"
        } else if ("word".equals(type)) {
            contentType = "application/vnd.ms-word"
        } else if ("pdf".equals(type)) {
            contentType = "application/pdf"
        } else if ("octet".equals(type)) {
            contentType = "application/octet-stream"
        } else if ("txt".equals(type)) {
            contentType = "text/plain"
        } else if ("png".equals(type)) {
            contentType = "text/html;charset=utf-8"
        }
        response.setContentType(contentType)

        var inputStream = FileInputStream(file)
        var os = response.outputStream
        var read = 0
        var b = ByteArray(4096)
        inputStream.use { input ->
            os.use {
                while ({ read = input.read(b);read }() != -1) {
                    it.write(read)
                }
            }
        }
        os.close()
        inputStream.close()
    }

    fun commonDownload(response: HttpServletResponse, path: String, head: String, type: String) {
        response.reset()
        var hz: String = ".xls"
        var contentType = "application/vnd.ms-excel"
        if ("word".equals(type)) {
            contentType = "application/vnd.ms-word"
            hz = ".doc"
        } else if ("pdf".equals(type)) {
            contentType = "application/pdf"
            hz = ".pdf"
        } else if ("octet".equals(type)) {


            contentType = "application/octet-stream"
            hz = ""
        }
        response.setContentType(contentType)

        var heads = head + hz
        var fileName = String(heads.toByteArray(), charset("ISO-8859-1"))
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName)

        var inputStream = FileInputStream(File(path))
        var os = response.outputStream
        var read = 0
        var b = ByteArray(4096)
        inputStream.use { input ->
            os.use {
                while ({ read = input.read(b);read }() != -1) {
                    it.write(read)
                }
            }
        }
        os.close()
        inputStream.close()
    }
}