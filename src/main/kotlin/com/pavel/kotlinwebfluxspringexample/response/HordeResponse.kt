package com.pavel.kotlinwebfluxspringexample.response

import com.pavel.kotlinwebfluxspringexample.model.Horde

class HordeResponse(
    val id: String,
    val name: String,
    val location: String
) {
    companion object {
        fun fromEntity(horde: Horde): HordeResponse =
            HordeResponse(
                id = horde.id!!,
                name = horde.name,
                location = horde.location
            )
    }
}