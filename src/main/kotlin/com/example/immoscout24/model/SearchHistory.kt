package com.example.immoscout24.model

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class SearchHistory(
        @Id @GeneratedValue
        var id: Long? = null,
        var addedAt: LocalDateTime = LocalDateTime.now(),
        var addedBy: String,
        var repoUrl: String,
        var numOfCommits: Long,
        var numOfPRs: Long,
        var readMe: String
)