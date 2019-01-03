package com.example.demo

import com.cgs.hb.base.APPINFO
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class DemoApplicationTests {

	@Test
	fun contextLoads() {
		var app= APPINFO("12","","","","","")
        app.FILE_PATH = "24"
        println(app.FILE_PATH)
	}

}

