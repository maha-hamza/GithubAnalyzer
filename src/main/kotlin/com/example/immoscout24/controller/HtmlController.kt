package com.example.immoscout24.controller

import com.example.immoscout24.repositories.SearchHistoryRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController(val repo: SearchHistoryRepository) {

    @GetMapping("/")
    fun home(model: Model): String {
        return "home"
    }

    @GetMapping("/landing")
    fun landing(model: Model): String {
        model["history"] = repo.findAll()
        return "landing"
    }

}
