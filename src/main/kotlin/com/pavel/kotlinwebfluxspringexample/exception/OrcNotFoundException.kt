package com.pavel.kotlinwebfluxspringexample.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class OrcNotFoundException(message: String) : RuntimeException(message)
