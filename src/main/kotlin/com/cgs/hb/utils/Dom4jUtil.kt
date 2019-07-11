package com.cgs.hb.utils

import org.dom4j.Document
import org.dom4j.DocumentException
import org.dom4j.Element
import org.dom4j.io.SAXReader
import java.io.File

class Dom4jUtil {
    companion object {
        private var mimeMap = LinkedHashMap<String, String>()

        fun getMimeTypeMap(): Map<String, String> {
            if (mimeMap.size > 0) {
                return mimeMap
            }
            var map = LinkedHashMap<String, String>()
            var reader = SAXReader()
            try {
                var path = "src/main/resources/mime-mapping.xml"
                var doc: Document = reader.read(File(path))
                var root: Element = doc.rootElement
                //泛型 使用 as 转换
                var list = root.elements("mime-mapping") as List<Element>
                for (item in list) {
                    var extension = item.element("extension").text
                    var mimeType = item.element("mime-type").text
                    map.put(extension, mimeType)
                }
            } catch (e: DocumentException) {
                e.printStackTrace()
            }
            mimeMap = map
            return mimeMap
        }
    }
}


