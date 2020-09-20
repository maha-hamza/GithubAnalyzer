package com.example.immoscout24.controller

import com.example.immoscout24.service.HtmlService
import com.example.immoscout24.valueobjects.AnalyzingInput
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class HtmlController(
        val htmlService: HtmlService
) {

    @GetMapping("/")
    fun index(model: Model): String {
        return "index"
    }

    @GetMapping("/landing")
    fun landing(model: Model,
                @RegisteredOAuth2AuthorizedClient authorizedClient: OAuth2AuthorizedClient,
                @AuthenticationPrincipal oauth2User: OAuth2User): String {
        val username = oauth2User.attributes["login"] as String
        model.addAttribute("userName", username)
        model["history"] = htmlService.prepareSearchHistoryForLoggedInUser(username = username)
        return "landing"
    }

    @PostMapping("/analyze")
    fun analyze(model: Model,
                @ModelAttribute("input") input: AnalyzingInput,
                @RegisteredOAuth2AuthorizedClient authorizedClient: OAuth2AuthorizedClient,
                @AuthenticationPrincipal oauth2User: OAuth2User): String {
        val loggedInUser = oauth2User.attributes["login"] as String
        model.addAttribute("userName", loggedInUser)

        model["history"] = htmlService
                .sendGitHubInputForAnalysisAndPrepareOutput(
                        loggedInUser = loggedInUser,
                        repoOwner = input.repoOwner,
                        repoName = input.repoName
                )

        return "landing"
    }


}