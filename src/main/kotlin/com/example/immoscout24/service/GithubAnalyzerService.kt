package com.example.immoscout24.service

import com.example.immoscout24.valueobjects.GithubRepository
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class GithubAnalyzerService {

    fun analyzeGithub(
            loggedInUser: String,
            repoOwner: String,
            repoName: String
    ) {

        val restTemplate = RestTemplate()
        val result = restTemplate.getForObject<GithubRepository>("https://api.github.com/search/repositories?q=repo:$repoOwner/$repoName ", GithubRepository::class.java)

        println(result)
    }
}