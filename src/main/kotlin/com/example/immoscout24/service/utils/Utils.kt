package com.example.immoscout24.service.utils

import com.example.immoscout24.exceptions.InvalidLoggedInUserException
import com.example.immoscout24.exceptions.InvalidRepoNameException
import com.example.immoscout24.exceptions.InvalidRepoOwnerException


fun validateLoggedInUser(user: String) {
    if (user.isBlank() || user.isEmpty())
        throw InvalidLoggedInUserException("[LoggedIn User] can't be Empty or Blank")
}

fun validateRepoOwner(repoOwner: String) {
    if (repoOwner.isBlank() || repoOwner.isEmpty())
        throw InvalidRepoOwnerException("[Repository Owner] can't be Empty or Blank")
}

fun validateRepoName(repo: String) {
    if (repo.isBlank() || repo.isEmpty())
        throw InvalidRepoNameException("[Repository Name] can't be Empty or Blank")
}