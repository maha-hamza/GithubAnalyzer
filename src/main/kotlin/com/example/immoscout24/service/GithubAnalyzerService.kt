package com.example.immoscout24.service

import com.example.immoscout24.exceptions.InvalidCommitsRequestException
import com.example.immoscout24.exceptions.InvalidPullRequestRequestException
import com.example.immoscout24.exceptions.InvalidReadMeRequestException
import com.example.immoscout24.exceptions.InvalidRepositoryRequestException
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
            throw InvalidRepositoryRequestException("Couldn't Identify Repository with the given input[$repoOwner:$repoName]")
        }
    }

    private fun getPullRequestsCount(url: String): Long {
        val result = Unirest.get(url)
                .asObject(Array<GithubItemContainer>::class.java)
        if (result.isSuccess) {
            return result.body.size.toLong()
        } else {
            throw InvalidPullRequestRequestException("Unexpected error while requesting [$url]")
        }
    }

    private fun getCommitsCount(url: String): Long {
        val ur = "https://api.github.com/repos/nestjs/nest-cli/commits?per_page=1 | sed -n '/^[Ll]ink:/ s/.*\"next\".*page=\\([0-9]*\\).*\"last\".*/\\1/p'"
        val result = Unirest.get(ur)
                .asObject(Array<GithubItemContainer>::class.java)
        return when {
            result.isSuccess -> result.body.size.toLong()
            result.status == 409 -> 0 // specific case for empty repos
            else -> throw InvalidCommitsRequestException("Unexpected error while requesting [$url]")
        }
    }

    private fun getReadMe(url: String): String? {
        val result = Unirest.get(url)
                .asObject(ReadMe::class.java)
        println(result.status)
        return when {
            result.isSuccess -> URL(result.body.download_url).readText()
            result.status == 404 -> null
            else -> throw InvalidReadMeRequestException("Unexpected error while requesting [$url]")
        }
    }
}