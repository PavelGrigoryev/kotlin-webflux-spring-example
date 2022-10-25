package com.pavel.kotlinwebfluxspringexample.controller

import com.pavel.kotlinwebfluxspringexample.request.OrcRequest
import com.pavel.kotlinwebfluxspringexample.response.OrcResponse
import com.pavel.kotlinwebfluxspringexample.service.OrcService
import org.bson.types.ObjectId
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/orcs")
class OrcController(private val orcService: OrcService) {

    @PostMapping
    fun createOrc(@RequestBody request: OrcRequest): Mono<OrcResponse> {
        return orcService.createOrc(request)
            .map { OrcResponse.fromEntity(it) }
    }

    @GetMapping
    fun findAll(): Flux<OrcResponse> {
        return orcService.findAll()
            .map { OrcResponse.fromEntity(it) }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: ObjectId): Mono<OrcResponse> {
        return orcService.findById(id)
            .map { OrcResponse.fromEntity(it) }
    }

    @GetMapping("/hordes/{hordeId}")
    fun findAllByHordeId(@PathVariable hordeId: String): Flux<OrcResponse> {
        return orcService.findAllByHordeId(hordeId)
            .map { OrcResponse.fromEntity(it) }
    }

    @PutMapping("/{id}")
    fun updateOrc(@PathVariable id: ObjectId, @RequestBody request: OrcRequest): Mono<OrcResponse> {
        return orcService.updateOrc(id, request)
            .map { OrcResponse.fromEntity(it) }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: ObjectId): Mono<Void> {
        return orcService.deleteById(id)
    }

}