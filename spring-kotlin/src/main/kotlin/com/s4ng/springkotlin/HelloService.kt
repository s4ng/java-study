package com.s4ng.springkotlin

import org.springframework.stereotype.Service

@Service
class HelloService {
    fun greeting(): String {
        return "hello world!"
    }
}