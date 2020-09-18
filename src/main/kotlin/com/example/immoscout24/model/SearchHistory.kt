package com.example.immoscout24.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class SearchHistory(
        @Id
        var id: String = UUID.randomUUID().toString(),
        var addedAt: LocalDateTime = LocalDateTime.now(),
        var addedBy: String,
        var repoUrl: String,
        var numOfCommits: Long,
        var numOfPrs: Long,
        var readMe: String?
)