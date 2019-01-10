package com.cgs.hb.dao

import com.cgs.hb.base.AppInfos
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppDao : JpaRepository<AppInfos, Long>