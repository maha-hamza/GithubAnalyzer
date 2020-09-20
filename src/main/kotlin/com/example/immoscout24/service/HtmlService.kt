package com.example.immoscout24.service

import com.example.immoscout24.exceptions.InvalidLoggedInUserException
import com.example.immoscout24.exceptions.InvalidRepoNameException
import com.example.immoscout24.exceptions.InvalidRepoOwnerException
import com.example.immoscout24.model.SearchHistory
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

    private fun validateLoggedInUser(user: String) {
        if (user.isBlank() || user.isEmpty())
            throw InvalidLoggedInUserException("[LoggedIn User] can't be Empty or Blank")
    }

    private fun validateRepoOwner(repoOwner: String) {
        if (repoOwner.isBlank() || repoOwner.isEmpty())
            throw InvalidRepoOwnerException("[Repository Owner] can't be Empty or Blank")
    }

    private fun validateRepoName(repo: String) {
        if (repo.isBlank() || repo.isEmpty())
            throw InvalidRepoNameException("[Repository Name] can't be Empty or Blank")
    }
}