package com.example.immoscout24.valueobjects

data class AnalysisResult(
        val repoUrl: String,
        val numOfCommits: Long,
        val numOfPrs: Long,
        val readMe: String
)