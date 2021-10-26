package pl.adrian.planningtripsbackend.services

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.adrian.planningtripsbackend.PlanningTripsBackendApplication
import pl.adrian.planningtripsbackend.config.TestSecurityConfiguration
import pl.adrian.planningtripsbackend.controllers.TripControllerTest
import pl.adrian.planningtripsbackend.trip.model.dto.TripDto
import pl.adrian.planningtripsbackend.trip.model.entity.Marker
import pl.adrian.planningtripsbackend.trip.model.entity.Position
import pl.adrian.planningtripsbackend.trip.model.entity.TravelMode
import pl.adrian.planningtripsbackend.trip.model.entity.Trip
import pl.adrian.planningtripsbackend.user.model.entity.Gender
import pl.adrian.planningtripsbackend.user.service.UserService
import java.time.Instant
import java.util.*

@SpringBootTest(classes = [PlanningTripsBackendApplication::class, TestSecurityConfiguration::class])
class UserServiceTestIT {

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun testCalculatingUserStatistics() {

        val userStatistics = userService.calculateUserStatistics(
            USER_TRIPS,
            DEFAULT_WEIGHT,
            DEFAULT_HEIGHT,
            DEFAULT_AGE,
            DEFAULT_GENDER
        )

        assertEquals(168, userStatistics.caloriesBurned)
        assertEquals(1.0, userStatistics.kilometersTraveled)
        assertEquals(1.0, userStatistics.hoursSpent)
    }

    companion object {
        private const val DEFAULT_HEIGHT = 180.0

        private const val DEFAULT_AGE = 22

        private const val DEFAULT_WEIGHT = 80.0

        private const val DEFAULT_DURATION = 1.0

        private const val DEFAULT_LENGTH = 4.0

        private val DEFAULT_GENDER = Gender.MALE

        private const val DEFAULT_ID = "id1"

        private val DEFAULT_CREATE_DATE = Instant.now()

        private const val DEFAULT_ROUTE = "{route: \"test\"}"

        private const val DEFAULT_ADDED_BY_USER_ID = "b8338d29-a826-47dc-9f6c-5adf3234ed7f"

        private const val DEFAULT_ESTIMATED_TIME = 1.0

        private const val DEFAULT_ESTIMATED_LENGTH = 1.0

        private val DEFAULT_TRAVEL_MODE = TravelMode.WALKING

        private const val DEFAULT_DONE = true

        private const val DEFAULT_MARKER_1_ID = "id1"

        private const val DEFAULT_MARKER_2_ID = "id2"

        private val DEFAULT_MARKER_TITLE = "title"

        private const val DEFAULT_MARKER_LAT = 1.0

        private const val DEFAULT_MARKER_LNG = 1.0

        private val DEFAULT_MARKERS = Arrays.asList(
            Marker(
                id = DEFAULT_MARKER_1_ID,
                title = DEFAULT_MARKER_TITLE,
                position = Position(
                    latitude = DEFAULT_MARKER_LAT,
                    longitude = DEFAULT_MARKER_LNG
                )
            ),
            Marker(
                id = DEFAULT_MARKER_2_ID,
                title = DEFAULT_MARKER_TITLE,
                position = Position(
                    latitude = DEFAULT_MARKER_LAT,
                    longitude = DEFAULT_MARKER_LNG
                )
            )
        )

        private val USER_TRIPS = listOf<TripDto>(
            TripDto(
                id = DEFAULT_ID,
                createdDate = DEFAULT_CREATE_DATE,
                route = DEFAULT_ROUTE,
                addedByUserId = DEFAULT_ADDED_BY_USER_ID,
                estimatedTime = DEFAULT_ESTIMATED_TIME,
                estimatedLength = DEFAULT_ESTIMATED_LENGTH,
                travelMode = DEFAULT_TRAVEL_MODE,
                markers = DEFAULT_MARKERS,
                done = DEFAULT_DONE
            )
        )
    }
}