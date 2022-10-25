package com.pavel.kotlinwebfluxspringexample.service.impl

import com.pavel.kotlinwebfluxspringexample.exception.OrcNotFoundException
import com.pavel.kotlinwebfluxspringexample.model.Orc
import com.pavel.kotlinwebfluxspringexample.repository.OrcRepository
import com.pavel.kotlinwebfluxspringexample.request.OrcRequest
import com.pavel.kotlinwebfluxspringexample.service.HordeService
import com.pavel.kotlinwebfluxspringexample.service.OrcService
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class OrcServiceImpl(
    private val hordeService: HordeService,
    private val orcRepository: OrcRepository
) : OrcService {

    override fun createOrc(request: OrcRequest): Mono<Orc> {
        val hordeId = request.hordeId

        return if (hordeId == null) {
            createOrcWithoutHorde(request)
        } else {
            createOrcWithHorde(hordeId, request)
        }
    }

    private fun createOrcWithoutHorde(request: OrcRequest): Mono<Orc> {
        return orcRepository.save(
            Orc(
                firstName = request.firstName,
                lastName = request.lastName,
                speciality = request.speciality,
                horde = null
            )
        ).log()
    }

    private fun createOrcWithHorde(hordeId: String, request: OrcRequest) =
        hordeService.findById(hordeId)
            .flatMap {
                orcRepository.save(
                    Orc(
                        firstName = request.firstName,
                        lastName = request.lastName,
                        speciality = request.speciality,
                        horde = it
                    )
                )
            }.log()

    override fun findAll(): Flux<Orc> = orcRepository.findAll().log()

    override fun findAllByHordeId(id: String): Flux<Orc> = orcRepository.findByHordeId(id).log()

    override fun findById(id: ObjectId): Mono<Orc> =
        orcRepository.findById(id)
            .switchIfEmpty {
                Mono.error(OrcNotFoundException("Orc with id $id not found"))
            }.log()

    override fun updateOrc(id: ObjectId, request: OrcRequest): Mono<Orc> {
        val orcToUpdate = findById(id)
        val hordeId = request.hordeId

        return if (hordeId == null) {
            updateOrcWithoutHorde(orcToUpdate, request)
        } else {
            updateOrcWithHorde(hordeId, orcToUpdate, request)
        }
    }

    private fun updateOrcWithoutHorde(orcToUpdate: Mono<Orc>, request: OrcRequest) =
        orcToUpdate.flatMap {
            orcRepository.save(it.apply {
                firstName = request.firstName
                lastName = request.lastName
                speciality = request.speciality
                horde = null
            })
        }.log()

    private fun updateOrcWithHorde(
        hordeId: String,
        orcToUpdate: Mono<Orc>,
        request: OrcRequest
    ) =
        hordeService.findById(hordeId)
            .zipWith(orcToUpdate)
            .flatMap {
                orcRepository.save(it.t2.apply {
                    firstName = request.firstName
                    lastName = request.lastName
                    speciality = request.speciality
                    horde = it.t1
                })
            }.log()

    override fun deleteById(id: ObjectId): Mono<Void> {
        return findById(id)
            .flatMap(orcRepository::delete).log()
    }
}