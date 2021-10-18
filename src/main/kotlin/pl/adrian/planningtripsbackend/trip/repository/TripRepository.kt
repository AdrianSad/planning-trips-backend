package pl.adrian.planningtripsbackend.trip.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import pl.adrian.planningtripsbackend.trip.model.entity.Trip

@Repository
interface TripRepository: MongoRepository<Trip, String> {
}