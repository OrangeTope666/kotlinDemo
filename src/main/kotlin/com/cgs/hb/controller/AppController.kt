package com.cgs.hb.controller

import com.cgs.hb.service.AppService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/appInfo")
@Api(description = "app")
class AppController {

    @Autowired
    private lateinit var appService: AppService

    @GetMapping(value = "/saveInfo")
    @ApiOperation(value = "保存信息")
    fun saveInfo() {
        appService.saveInfo()
    }
}