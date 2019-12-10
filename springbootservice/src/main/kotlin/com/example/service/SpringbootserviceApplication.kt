package com.example.service

import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import springfox.documentation.swagger2.annotations.EnableSwagger2
import template.springboot.demo.applicationListener.ApplicationListenerForStart

@ServletComponentScan
@SpringBootApplication
@EnableSwagger2
class SpringbootserviceApplication

fun main(args: Array<String>) {
    val application = SpringApplication(SpringbootserviceApplication::class.java)
    application.setBannerMode(Banner.Mode.OFF);
    //添加监听
    application.addListeners(ApplicationListenerForStart())
    application.run(*args)
}
