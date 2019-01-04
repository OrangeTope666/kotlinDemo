package com.cgs.hb.utils

import org.springframework.web.multipart.MultipartException
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import java.io.*
import java.time.Instant
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class FileUtil {
    fun uploadFile(file: MultipartFile, request: HttpServletRequest): String {
        if (!file.isEmpty) {
            var saveFileName = file.originalFilename
            var saveFile = File(request.session.servletContext.getRealPath("/upload/") + saveFileName)
            if (!saveFile.parentFile.exists()) {
                saveFile.parentFile.mkdirs()
            }
            var out = BufferedOutputStream(FileOutputStream(saveFile))
            out.write(file.bytes)
            out.flush()
            out.close()
            return "success"
        } else {
            return "空文件"
        }
    }

    fun uploadFiles(request: HttpServletRequest): String {
        var savePath = File(request.session.servletContext.getRealPath("/upload/"))
        if (!savePath.exists()) {
            savePath.mkdirs()
        }
        var files = (request as MultipartHttpServletRequest).getFiles("files")
        for (file in files) {
            var bytes = file.bytes
            var saveFile = File(savePath, file.originalFilename)
            var stream = BufferedOutputStream(FileOutputStream(saveFile))
            stream.write(bytes)
            stream.close()
        }
        return ""
    }

    fun downloadFile(filePath: String, fileName: String, response: HttpServletResponse) {
        response.reset()
        response.characterEncoding = "UTF-8"
        response.contentType = "application/force-download"
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName)
        try {
            var input = FileInputStream(File(filePath))
            var bis = BufferedInputStream(input)
            var os = response.outputStream
            var bos = BufferedOutputStream(os)
            var b = ByteArray(input.available())
            var read = 0
            bis.use { bi ->
                bos.use {
                    while ({ read = bi.read(b);read }() != -1) {
                        it.write(read)
                    }
                }
            }
            bos.flush()
            bos.close()
            bis.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun download(files: List<File>, response: HttpServletResponse) {
        response.reset()
        response.characterEncoding = "UTF-8"
        response.contentType = "application/octet-stream"
        var ins = Instant.now()
        var zipName = ins.toEpochMilli().toString() + ".zip"
        response.setHeader("Content-Disposition", "attachment;filename=" + zipName)
        try {
            var byte = ByteArray(10240)
            var os = response.outputStream
            var zos = ZipOutputStream(os)
            for (file in files) {
                var fis = FileInputStream(file)
                zos.putNextEntry(ZipEntry(file.name))
                var buf = BufferedInputStream(fis, 10240)
                var read = 0
                buf.use { z ->
                    zos.use {
                        while ({ read = z.read(byte);read }() != -1) {
                            it.write(read)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}