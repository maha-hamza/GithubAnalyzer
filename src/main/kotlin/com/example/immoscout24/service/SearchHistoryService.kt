package com.example.immoscout24.service

import com.example.immoscout24.model.SearchHistory
import com.example.immoscout24.repositories.SearchHistoryRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SearchHistoryService(val searchHistoryRepository: SearchHistoryRepository) {

    fun findUserSearchResult(username: String) = searchHistoryRepository
            .findAllByAddedBy(username)
            .sortedByDescending { it.addedAt }

    fun saveSearchHistory(
            repoUrl: String,
            numOfPr: Long,
            numOfCommits: Long,
            addedBy: String,
            readme: String?
    ) {
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
    }
}