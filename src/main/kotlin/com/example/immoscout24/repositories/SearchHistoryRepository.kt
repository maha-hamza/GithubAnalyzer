package com.example.immoscout24.repositories

import com.example.immoscout24.model.SearchHistory
import org.springframework.data.repository.CrudRepository

interface SearchHistoryRepository : CrudRepository<SearchHistory, Long> {
    fun findAllByAddedBy(addedBy: String): List<SearchHistory>
}