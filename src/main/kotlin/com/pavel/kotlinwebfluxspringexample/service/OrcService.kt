package com.pavel.kotlinwebfluxspringexample.service

import com.pavel.kotlinwebfluxspringexample.model.Orc
import com.pavel.kotlinwebfluxspringexample.request.OrcRequest
import org.bson.types.ObjectId
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface OrcService {

    fun createOrc(request: OrcRequest): Mono<Orc>

    fun findAll(): Flux<Orc>

    fun findAllByHordeId(id: String): Flux<Orc>

    fun findById(id: ObjectId): Mono<Orc>

    fun updateOrc(id: ObjectId, request: OrcRequest): Mono<Orc>

    fun deleteById(id: ObjectId): Mono<Void>

}