package com.cgs.hb.utils

import java.io.File
import java.text.DecimalFormat

class FileOrFileDirUtil {
    fun getDirSize(dir: File): Long {
        if (dir == null) return 0
        if (!dir.isDirectory) return 0

        var dirSize: Long = 0
        var files = dir.listFiles()
        for (file in files) {
            if (file.isFile) {
                dirSize += file.length()
            } else if (file.isDirectory) {
                dirSize += file.length()
                dirSize += getDirSize(file)
            }
        }
        return dirSize
    }

    fun formetFileSize(fileSize: Long): String {
        var df = DecimalFormat("#.00")
        var fs = ""
        if (fileSize < 1024) {
            fs = df.format(fileSize) + "B"
        } else if (fileSize < 1048576) {
            fs = df.format(fileSize / 1024) + "KB"
        } else if (fileSize < 1024 * 1024 * 1024) {
            fs = df.format(fileSize / (1024 * 1024)) + "MB"
        } else {
            fs = df.format(fileSize / (1024 * 1024 * 1024)) + "GB"
        }
        return fs
    }

    fun createDir(path: String): File? {
        var file: File? = null
        file = File(path)
        if (!(file.exists()) && !(file.isDirectory)) {
            file.mkdirs()
        }
        return file
    }
}