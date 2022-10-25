package com.pavel.kotlinwebfluxspringexample.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "hordes")
data class Horde(

    @Id
    val id: String? = null,

    var name: String,

    @Field(name = "horde_location")
    var location: String

)
