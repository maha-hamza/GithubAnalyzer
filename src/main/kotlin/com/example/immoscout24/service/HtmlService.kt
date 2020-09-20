package com.example.immoscout24.service

import com.example.immoscout24.model.SearchHistory
import com.example.immoscout24.service.utils.validateLoggedInUser
import com.example.immoscout24.service.utils.validateRepoName
import com.example.immoscout24.service.utils.validateRepoOwner
import org.springframework.stereotype.Service

@Service
class HtmlService(
        val historyService: SearchHistoryService,
        val githubAnalyzerService: GithubAnalyzerService
) {

    fun prepareSearchHistoryForLoggedInUser(username: String): List<SearchHistory> {
        validateLoggedInUser(username)
        return historyService
                .findUserSearchResult(username)
    }

    fun sendGitHubInputForAnalysisAndPrepareOutput(loggedInUser: String,
                                                   repoOwner: String,
                                                   repoName: String): List<SearchHistory> {
        validateLoggedInUser(loggedInUser)
        validateRepoName(repoName)
        validateRepoOwner(repoOwner)

        val result = githubAnalyzerService.analyzeGithub(loggedInUser, repoOwner, repoName)
        historyService.saveSearchHistory(
                repoUrl = result.repoUrl,
                numOfCommits = result.numOfCommits,
                numOfPr = result.numOfPrs,
                addedBy = loggedInUser,
                readme = result.readMe
        )
        return prepareSearchHistoryForLoggedInUser(loggedInUser)
    }
}