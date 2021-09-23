package pl.adrian.planningtripsbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
class PlanningTripsBackendApplication

fun main(args: Array<String>) {
    runApplication<PlanningTripsBackendApplication>(*args)
}
