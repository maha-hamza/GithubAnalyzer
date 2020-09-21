package com.example.immoscout24.service

import com.example.immoscout24.exceptions.InvalidLoggedInUserException
import com.example.immoscout24.exceptions.InvalidRepoNameException
import com.example.immoscout24.exceptions.InvalidRepoOwnerException
import com.example.immoscout24.model.SearchHistory
import com.example.immoscout24.valueobjects.AnalysisResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doNothing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime
import java.util.*

@ActiveProfiles("test")
@SpringBootTest
class HtmlServiceTest {

    @Autowired
    private lateinit var service: HtmlService

    @MockBean
    private lateinit var historyService: SearchHistoryService

    @MockBean
    private lateinit var githubAnalyzerService: GithubAnalyzerService

    @Test
    fun `should analyse github successfully`() {
        `when`(githubAnalyzerService.analyzeGithub("user", "owner", "repo"))
                .thenReturn(
                        AnalysisResult(
                                repoUrl = "http://github/repo",
                                numOfCommits = 5,
                                numOfPrs = 2,
                                readMe = ""
                        )
                )
        doNothing().`when`(historyService).saveSearchHistory(
                repoUrl = "http://github/repo",
                numOfCommits = 5,
                numOfPr = 2,
                readme = "",
                addedBy = "user"
        )
        `when`(historyService.findUserSearchResult("user")).thenReturn(
                listOf(
                        SearchHistory(
                                repoUrl = "http://github/repo",
                                numOfCommits = 5,
                                numOfPrs = 2,
                                readMe = "",
                                addedBy = "user",
                                addedAt = LocalDateTime.now(),
                                id = UUID.randomUUID().toString()
                        )
                )
        )
        val result = service.sendGitHubInputForAnalysisAndPrepareOutput("user", "owner", "repo")

        assertThat(result).usingElementComparatorIgnoringFields("addedAt", "id")
                .contains(
                        *listOf(
                                SearchHistory(
                                        repoUrl = "http://github/repo",
                                        numOfCommits = 5,
                                        numOfPrs = 2,
                                        readMe = "",
                                        addedBy = "user",
                                        addedAt = LocalDateTime.now(),
                                        id = UUID.randomUUID().toString()
                                )
                        ).toTypedArray()
                )
    }

    @Test
    fun `should not analyse github if invalid loggedin user provided`() {
        assertThrows<InvalidLoggedInUserException>("[LoggedIn User] can't be Empty or Blank") {
            service.sendGitHubInputForAnalysisAndPrepareOutput("", "owner", "repo")
        }
    }

    @Test
    fun `should not analyse github if blank repo submitted`() {
        assertThrows<InvalidRepoNameException>("[Repository Name] can't be Empty or Blank") {
            service.sendGitHubInputForAnalysisAndPrepareOutput("user", "owner", "")
        }
    }

    @Test
    fun `should not analyse github if blank repo owner submitted`() {
        assertThrows<InvalidRepoOwnerException>("[Repository Owner] can't be Empty or Blank") {
            service.sendGitHubInputForAnalysisAndPrepareOutput("user", "", "repo")
        }
    }

    @Test
    fun `should return all search history for user`() {
        `when`(historyService.findUserSearchResult("user")).thenReturn(
                listOf(
                        SearchHistory(
                                repoUrl = "http://github/repo",
                                numOfCommits = 5,
                                numOfPrs = 2,
                                readMe = "",
                                addedBy = "user",
                                addedAt = LocalDateTime.now(),
                                id = UUID.randomUUID().toString()
                        ),
                        SearchHistory(
                                repoUrl = "http://github/repo2",
                                numOfCommits = 55,
                                numOfPrs = 0,
                                readMe = "",
                                addedBy = "user",
                                addedAt = LocalDateTime.now(),
                                id = UUID.randomUUID().toString()
                        ),
                        SearchHistory(
                                repoUrl = "http://github/repo6",
                                numOfCommits = 1,
                                numOfPrs = 0,
                                readMe = "",
                                addedBy = "user2",
                                addedAt = LocalDateTime.now(),
                                id = UUID.randomUUID().toString()
                        )
                )
        )
        val result = service.prepareSearchHistoryForLoggedInUser("user")

        assertThat(result).usingElementComparatorIgnoringFields("addedAt", "id")
                .contains(
                        *listOf(
                                SearchHistory(
                                        repoUrl = "http://github/repo",
                                        numOfCommits = 5,
                                        numOfPrs = 2,
                                        readMe = "",
                                        addedBy = "user",
                                        addedAt = LocalDateTime.now(),
                                        id = UUID.randomUUID().toString()
                                ),
                                SearchHistory(
                                        repoUrl = "http://github/repo2",
                                        numOfCommits = 55,
                                        numOfPrs = 0,
                                        readMe = "",
                                        addedBy = "user",
                                        addedAt = LocalDateTime.now(),
                                        id = UUID.randomUUID().toString()
                                )
                        ).toTypedArray()
                )
    }

    @Test
    fun `should return empty list if user has no history`() {
        `when`(historyService.findUserSearchResult("user")).thenReturn(
                emptyList()
        )
        val result = service.prepareSearchHistoryForLoggedInUser("user")
        assertThat(result).isEmpty()
    }
}