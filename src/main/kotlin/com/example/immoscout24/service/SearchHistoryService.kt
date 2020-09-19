package com.example.immoscout24.service

import com.example.immoscout24.repositories.SearchHistoryRepository
import org.springframework.stereotype.Service

@Service
class SearchHistoryService(val searchHistoryRepository: SearchHistoryRepository) {

    fun findUserSearchResult(username: String) = searchHistoryRepository
            .findAllByAddedBy(username)
            .sortedByDescending { it.addedAt }
}