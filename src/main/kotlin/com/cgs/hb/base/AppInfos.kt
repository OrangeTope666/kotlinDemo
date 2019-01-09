package com.cgs.hb.base

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "AppInfo")
data class AppInfo(
        @Id
        private var id: Long?,
        private var fileSize: String?,
        private var filePath: String?,
        private var fileName: String?,
        private var version: String?,
        private var uploadTime: String?
) {
    constructor() : this(null, null, null, null, null, null)
}
