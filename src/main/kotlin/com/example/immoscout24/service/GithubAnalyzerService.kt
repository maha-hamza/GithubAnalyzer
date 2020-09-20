package com.example.immoscout24.service

import com.example.immoscout24.valueobjects.GithubItemContainer
import com.example.immoscout24.valueobjects.GithubRepository
import com.example.immoscout24.valueobjects.ReadMe
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.net.URL


@Service
class GithubAnalyzerService {

    fun analyzeGithub(
            loggedInUser: String,
            repoOwner: String,
            repoName: String
    ): AnalysisResult {

        val restTemplate = RestTemplate()
        val result = restTemplate.getForObject<GithubRepository>("https://api.github.com/search/repositories?q=repo:$repoOwner/$repoName", GithubRepository::class.java)!!

        var numOfCommits = 0
        //TODO fix it
//        for (i in 1..20) {
//            numOfCommits += restTemplate.getForObject<List<GithubItemContainer>>(result.items[0].commits_url.replace("{/sha}", "") + "?page=$i", List::class.java).count()
//        }
        numOfCommits = restTemplate.getForObject<List<GithubItemContainer>>(result.items[0].commits_url.replace("{/sha}", "") + "?per_page=100", List::class.java).count()
        val numOfPrs = restTemplate.getForObject<List<GithubItemContainer>>(result.items[0].pulls_url.replace("{/number}", ""), List::class.java).count()
        val readme = restTemplate.getForObject<ReadMe>("https://api.github.com/repos/$repoOwner/$repoName/readme", ReadMe::class.java)
        val content = URL(readme?.download_url).readText()

        return AnalysisResult(
                repoUrl = result.items[0].html_url,
                numOfCommits = numOfCommits.toLong(),
                readMe = content,
                numOfPrs = numOfPrs.toLong()
        )
    }
}

data class AnalysisResult(
        val repoUrl: String,
        val numOfCommits: Long,
        val numOfPrs: Long,
        val readMe: String
)