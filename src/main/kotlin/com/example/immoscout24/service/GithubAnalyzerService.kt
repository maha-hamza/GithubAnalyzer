package com.example.immoscout24.service

import com.example.immoscout24.valueobjects.GithubItemContainer
import com.example.immoscout24.valueobjects.GithubRepository
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject


@Service
class GithubAnalyzerService(val historyService: SearchHistoryService) {

    fun analyzeGithub(
            loggedInUser: String,
            repoOwner: String,
            repoName: String
    ) {

        val restTemplate = RestTemplate()
        val result = restTemplate.getForObject<GithubRepository>("https://api.github.com/search/repositories?q=repo:$repoOwner/$repoName", GithubRepository::class.java)!!

        val numOfCommits = restTemplate.getForObject<List<GithubItemContainer>>(result.items[0].commits_url.replace("{/sha}", "") + "?per_page=${Int.MAX_VALUE}", List::class.java).count()
        val numOfPrs = restTemplate.getForObject<List<GithubItemContainer>>(result.items[0].pulls_url.replace("{/number}", ""), List::class.java).count()

        historyService.saveSearchHistory(
                repoUrl = result.items[0].html_url,
                numOfCommits = numOfCommits.toLong(),
                numOfPr = numOfPrs.toLong(),
                addedBy = loggedInUser
        )
        println(result)
    }
}