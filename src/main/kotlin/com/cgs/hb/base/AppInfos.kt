package com.cgs.hb.base

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "app_info")
data class AppInfos(
        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        var id: Long?,
        var fileSize: String?,
        var filePath: String?,
        var fileName: String?,
        var version: String?,
        var uploadTime: String?
) {
    constructor() : this(null, null, null, null, null, null)
}
