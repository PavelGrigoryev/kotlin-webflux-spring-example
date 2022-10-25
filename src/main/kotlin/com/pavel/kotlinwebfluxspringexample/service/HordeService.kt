package com.pavel.kotlinwebfluxspringexample.service

import com.pavel.kotlinwebfluxspringexample.model.Horde
import com.pavel.kotlinwebfluxspringexample.request.HordeRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface HordeService {

    fun createHorde(request: HordeRequest): Mono<Horde>

    fun findAll(): Flux<Horde>

    fun findById(id: String): Mono<Horde>

    fun updateHorde(id: String, request: HordeRequest): Mono<Horde>

    fun deleteById(id: String): Mono<Void>

}