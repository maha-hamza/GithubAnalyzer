package com.example.immoscout24.service

import com.example.immoscout24.exceptions.InvalidCommitsRequest
import com.example.immoscout24.exceptions.InvalidPullRequestRequest
import com.example.immoscout24.exceptions.InvalidReadMeRequest
import com.example.immoscout24.exceptions.InvalidRepositoryRequest
import com.example.immoscout24.service.utils.validateLoggedInUser
import com.example.immoscout24.service.utils.validateRepoName
import com.example.immoscout24.service.utils.validateRepoOwner
import com.example.immoscout24.valueobjects.AnalysisResult
import com.example.immoscout24.valueobjects.GithubItemContainer
import com.example.immoscout24.valueobjects.GithubRepository
import com.example.immoscout24.valueobjects.ReadMe
import kong.unirest.Unirest
import org.springframework.stereotype.Service
import java.net.URL

@Service
class GithubAnalyzerService {

    fun analyzeGithub(
            loggedInUser: String,
            repoOwner: String,
            repoName: String
    ): AnalysisResult {

        validateLoggedInUser(loggedInUser)
        validateRepoName(repoName)
        validateRepoOwner(repoOwner)

        val response = Unirest.get("https://api.github.com/repos/$repoOwner/$repoName")
                .asObject(GithubRepository::class.java)

        if (response.isSuccess) {
            val body = response.body
            val pullRequestsCount = getPullRequestsCount(body.preparePullsUrl().plus("?per_page=100"))
            val pullCommitsCount = getCommitsCount(body.prepareCommitUrl().plus("?per_page=100"))
            val content = getReadMe("https://api.github.com/repos/$repoOwner/$repoName/readme")
            return AnalysisResult(
                    repoUrl = body.html_url,
                    numOfCommits = pullCommitsCount,
                    readMe = content,
                    numOfPrs = pullRequestsCount
            )
        } else {
            throw InvalidRepositoryRequest("Couldn't Identify Repository with the given input[$repoOwner:$repoName]")
        }
    }

    private fun getPullRequestsCount(url: String): Long {
        val result = Unirest.get(url)
                .asObject(Array<GithubItemContainer>::class.java)
        if (result.isSuccess) {
            return result.body.size.toLong()
        } else {
            throw InvalidPullRequestRequest("Unexpected error while requesting [$url]")
        }
    }

    private fun getCommitsCount(url: String): Long {
        val result = Unirest.get(url)
                .asObject(Array<GithubItemContainer>::class.java)
        if (result.isSuccess) {
            return result.body.size.toLong()
        } else {
            throw InvalidCommitsRequest("Unexpected error while requesting [$url]")
        }
    }

    private fun getReadMe(url: String): String {
        val result = Unirest.get(url)
                .asObject(ReadMe::class.java)
        if (result.isSuccess) {
            return URL(result.body.download_url).readText()
        } else {
            throw InvalidReadMeRequest("Unexpected error while requesting [$url]")
        }
    }
}