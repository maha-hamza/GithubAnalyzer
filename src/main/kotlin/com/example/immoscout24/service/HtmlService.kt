package com.example.immoscout24.service

import com.example.immoscout24.model.SearchHistory
import org.springframework.stereotype.Service

@Service
class HtmlService(
        val historyService: SearchHistoryService,
        val githubAnalyzerService: GithubAnalyzerService
) {

    fun prepareSearchHistoryForLoggedInUser(username: String) = historyService
            .findUserSearchResult(username)

    fun sendGitHubInputForAnalysisAndPrepareOutput(loggedInUser: String,
                                                   repoOwner: String,
                                                   repoName: String): List<SearchHistory> {

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