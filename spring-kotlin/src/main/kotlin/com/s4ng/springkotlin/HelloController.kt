package com.s4ng.springkotlin

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(private val helloService: HelloService) { // <- 클래스에 바로 생성자 함수 선언 후, 인자로 주입받음.
                                                                // 만약 주입받을 빈이 여러개라면 @Authwired를 추가해야 함.
    @GetMapping("/hello")
    fun greeting(): String{
        return helloService.greeting();
    }
}