package com.pavel.kotlinwebfluxspringexample.controller

import com.pavel.kotlinwebfluxspringexample.request.HordeRequest
import com.pavel.kotlinwebfluxspringexample.response.HordeResponse
import com.pavel.kotlinwebfluxspringexample.service.HordeService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/hordes")
class HordeController(private val hordeService: HordeService) {

    @PostMapping
    fun createHorde(@RequestBody request: HordeRequest): Mono<HordeResponse> {
        return hordeService.createHorde(request)
            .map { HordeResponse.fromEntity(it) }
    }

    @GetMapping
    fun findAll(): Flux<HordeResponse> {
        return hordeService.findAll()
            .map { HordeResponse.fromEntity(it) }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): Mono<HordeResponse> {
        return hordeService.findById(id)
            .map { HordeResponse.fromEntity(it) }
    }

    @PutMapping("/{id}")
    fun updateHorde(@PathVariable id: String, @RequestBody request: HordeRequest): Mono<HordeResponse> {
        return hordeService.updateHorde(id, request)
            .map { HordeResponse.fromEntity(it) }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: String): Mono<Void> {
        return hordeService.deleteById(id)
    }

}