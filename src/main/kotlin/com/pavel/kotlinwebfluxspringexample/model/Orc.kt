package com.pavel.kotlinwebfluxspringexample.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "orcs")
data class Orc(

    @Id
    val id: ObjectId? = null,

    var firstName: String,

    var lastName: String,

    var speciality: String,

    var horde: Horde?

)
