package com.example.immoscout24.service

import com.example.immoscout24.exceptions.InvalidSearchHistoryOperationException
import com.example.immoscout24.model.SearchHistory
import com.example.immoscout24.repositories.SearchHistoryRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SearchHistoryService(val searchHistoryRepository: SearchHistoryRepository) {

    fun findUserSearchResult(username: String) = searchHistoryRepository
            .findAllByAddedByOrderByAddedAtDesc(username)

    fun saveSearchHistory(
            repoUrl: String,
            numOfPr: Long,
            numOfCommits: Long,
            addedBy: String,
            readme: String?
    ) {
        try {
            searchHistoryRepository.save(
                    SearchHistory(
                            addedBy = addedBy,
                            numOfCommits = numOfCommits,
                            numOfPrs = numOfPr,
                            addedAt = LocalDateTime.now(),
                            readMe = readme,
                            repoUrl = repoUrl
                    )
            )
        } catch (e: Exception) {
            throw InvalidSearchHistoryOperationException("Something Wrong While saving entry: [${e.stackTrace}]")
        }
    }
}