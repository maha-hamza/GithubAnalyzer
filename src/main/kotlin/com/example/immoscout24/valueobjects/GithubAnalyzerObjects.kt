package com.example.immoscout24.valueobjects

data class GithubRepository(
        val html_url: String,
        val commits_url: String,
        val pulls_url: String
) {
    fun prepareCommitUrl() = commits_url.replace("{/sha}", "")
    fun preparePullsUrl() = pulls_url.replace("{/number}", "")
}

data class GithubItemContainer(
        val node_id: String
)

data class ReadMe(
        val download_url: String
)