package com.example.immoscout24.exceptions

import java.lang.RuntimeException

class InvalidLoggedInUserException(msg: String) : RuntimeException(msg)
class InvalidRepoOwnerException(msg: String) : RuntimeException(msg)
class InvalidRepoNameException(msg: String) : RuntimeException(msg)
class InvalidRepositoryRequestException(msg: String) : RuntimeException(msg)
class InvalidPullRequestRequestException(msg: String) : RuntimeException(msg)
class InvalidCommitsRequestException(msg: String) : RuntimeException(msg)
class InvalidReadMeRequestException(msg: String) : RuntimeException(msg)
class InvalidSearchHistoryOperationException(msg: String) : RuntimeException(msg)