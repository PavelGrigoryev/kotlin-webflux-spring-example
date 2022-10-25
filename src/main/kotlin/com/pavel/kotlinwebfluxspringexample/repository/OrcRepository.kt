package com.pavel.kotlinwebfluxspringexample.repository

import com.pavel.kotlinwebfluxspringexample.model.Orc
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface OrcRepository : ReactiveMongoRepository<Orc, ObjectId> {

    fun findByHordeId(id: String): Flux<Orc>

}