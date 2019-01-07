package com.cgs.hb.config;

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.MultipartConfigFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.unit.DataSize
import javax.servlet.MultipartConfigElement

/**
 * @author chengz
 * @date 2019/1/7 10:22
 */
@Configuration
class FileUploadConfig {

    @Value("\${spring.servlet.multipart.max-file-size}")
    lateinit var maxFileSize: DataSize

    @Value("\${spring.servlet.multipart.max-request-size}")
    lateinit var maxRequestSize: DataSize

    @Bean
    fun multipartConfigElement(): MultipartConfigElement{
        var factory = MultipartConfigFactory()
        factory.setMaxFileSize(maxFileSize)
        factory.setMaxRequestSize(maxRequestSize)
        return factory.createMultipartConfig()
    }
}
