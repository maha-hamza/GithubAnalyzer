package com.example.immoscout24.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController {
  
    @GetMapping("/")
    fun home(model: Model): String {
        return "home"
    }

}
