package com.cgs.hb.service

import com.cgs.hb.base.AppInfos
import com.cgs.hb.dao.AppDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class AppService {

    @Autowired
    @Qualifier("appDao")
    lateinit var appDo: AppDao

    fun saveInfo() {
        var app = AppInfos(1, "1MB", "c:\\app", "test.app","1.0.0","2018-12-01")
        appDo.save(app)
    }
}