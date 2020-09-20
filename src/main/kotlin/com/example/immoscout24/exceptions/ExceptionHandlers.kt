package com.example.immoscout24.exceptions

import java.lang.RuntimeException

class InvalidLoggedInUserException(msg: String) : RuntimeException(msg)
class InvalidRepoOwnerException(msg: String) : RuntimeException(msg)
class InvalidRepoNameException(msg: String) : RuntimeException(msg)