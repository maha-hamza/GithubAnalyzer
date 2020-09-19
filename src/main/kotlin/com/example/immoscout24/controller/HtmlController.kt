package com.example.immoscout24.controller

import com.example.immoscout24.service.GithubAnalyzerService
import com.example.immoscout24.service.SearchHistoryService
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
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class HtmlController(
        val historyService: SearchHistoryService,
        val githubAnalyzerService: GithubAnalyzerService
) {

    @RequestMapping("/securedPage")
    fun securedPage(model: Model,
                    @RegisteredOAuth2AuthorizedClient authorizedClient: OAuth2AuthorizedClient,
                    @AuthenticationPrincipal oauth2User: OAuth2User): String? {
        model.addAttribute("userName", oauth2User.attributes["login"])
        model.addAttribute("clientName", authorizedClient.clientRegistration.clientName)
        model.addAttribute("userAttributes", oauth2User.attributes)
        return "securedPage"
    }

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
        model["history"] = historyService.findUserSearchResult(username = username)
        return "landing"
    }

    @PostMapping("/analyze")
    fun analyze(model: Model,
                @ModelAttribute("input") input: AnalyzingInput,
                @RegisteredOAuth2AuthorizedClient authorizedClient: OAuth2AuthorizedClient,
                @AuthenticationPrincipal oauth2User: OAuth2User): String {
        val loggedInUser = oauth2User.attributes["login"] as String
        model.addAttribute("userName", loggedInUser)

        githubAnalyzerService
                .analyzeGithub(
                        loggedInUser = loggedInUser,
                        repoOwner = input.repoOwner,
                        repoName = input.repoName
                )
        model["history"] = historyService.findUserSearchResult(username = loggedInUser)
        return "landing"
    }


}