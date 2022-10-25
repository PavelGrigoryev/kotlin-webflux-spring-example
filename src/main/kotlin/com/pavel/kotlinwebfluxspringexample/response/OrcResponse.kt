package com.pavel.kotlinwebfluxspringexample.response

import com.pavel.kotlinwebfluxspringexample.model.Orc

class OrcResponse(
    val id: String,
    val firstName: String,
    val lastName: String,
    val speciality: String,
    val horde: HordeResponse?
) {
    companion object {
        fun fromEntity(orc: Orc): OrcResponse =
            OrcResponse(
                id = orc.id!!.toHexString(),
                firstName = orc.firstName,
                lastName = orc.lastName,
                speciality = orc.speciality,
                horde = orc.horde?.let { HordeResponse.fromEntity(it) }
            )
    }
}