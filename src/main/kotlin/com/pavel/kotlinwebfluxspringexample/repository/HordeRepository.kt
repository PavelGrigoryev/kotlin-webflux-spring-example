package com.pavel.kotlinwebfluxspringexample.repository

import com.pavel.kotlinwebfluxspringexample.model.Horde
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface HordeRepository : ReactiveMongoRepository<Horde, String>