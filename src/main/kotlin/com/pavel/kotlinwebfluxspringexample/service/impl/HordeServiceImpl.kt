package com.pavel.kotlinwebfluxspringexample.service.impl

import com.pavel.kotlinwebfluxspringexample.exception.HordeNotFoundException
import com.pavel.kotlinwebfluxspringexample.model.Horde
import com.pavel.kotlinwebfluxspringexample.model.Orc
import com.pavel.kotlinwebfluxspringexample.repository.HordeRepository
import com.pavel.kotlinwebfluxspringexample.repository.OrcRepository
import com.pavel.kotlinwebfluxspringexample.request.HordeRequest
import com.pavel.kotlinwebfluxspringexample.service.HordeService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class HordeServiceImpl(
    private val hordeRepository: HordeRepository,
    private val orcRepository: OrcRepository
) : HordeService {

    override fun createHorde(request: HordeRequest): Mono<Horde> =
        hordeRepository.save(Horde(name = request.name, location = request.location)).log()

    override fun findAll(): Flux<Horde> = hordeRepository.findAll().log()

    override fun findById(id: String): Mono<Horde> =
        hordeRepository.findById(id)
            .switchIfEmpty(Mono.error(HordeNotFoundException("Horde with id $id not found"))).log()

    override fun updateHorde(id: String, request: HordeRequest): Mono<Horde> =
        findById(id)
            .flatMap {
                hordeRepository.save(it.apply {
                    name = request.name
                    location = request.location
                })
            }.doOnSuccess { updateHordeOfOrcs(it).subscribe() }.log()

    override fun deleteById(id: String): Mono<Void> = findById(id).flatMap(hordeRepository::delete).log()

    private fun updateHordeOfOrcs(updatedHorde: Horde): Flux<Orc> =
        orcRepository.saveAll(
            orcRepository.findByHordeId(updatedHorde.id!!)
                .map { it.apply { horde = updatedHorde } }
        ).log()
}