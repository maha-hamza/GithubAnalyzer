package com.example.immoscout24.valueobjects

import java.util.*

data class GithubRepository(
        val items: List<Item>
)

data class Item(
        val private: Boolean,
        val html_url: String,
        val commits_url: String,
        val pulls_url: String
)

data class GithubItemContainer(
        val node_id: String
)

data class ReadMe(
        val download_url: String
)