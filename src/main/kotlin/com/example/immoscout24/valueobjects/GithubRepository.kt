package com.example.immoscout24.valueobjects

data class GithubRepository(
        val items: List<Item>
)

data class Item(
        val private: Boolean,
        val html_url: String,
        val commits_url: String,
        val pulls_url: String
)