package com.cgs.hb.base

data class APPINFO(
        var ID:String?,
        var FILE_SIZE:String?,
        var VERSION:String?,
        var UPLOAD_TIME:String?,
        var FILE_PATH:String?,
        var FILE_NAME:String?

) {
    constructor() : this(null, null, null, null, null, null)
}
