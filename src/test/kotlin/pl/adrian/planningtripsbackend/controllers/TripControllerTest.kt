package pl.adrian.planningtripsbackend.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.TestSecurityContextHolder
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import pl.adrian.planningtripsbackend.PlanningTripsBackendApplication
import pl.adrian.planningtripsbackend.config.TestSecurityConfiguration
import pl.adrian.planningtripsbackend.trip.mapper.TripMapper
import pl.adrian.planningtripsbackend.trip.model.entity.Marker
import pl.adrian.planningtripsbackend.trip.model.entity.Position
import pl.adrian.planningtripsbackend.trip.model.entity.TravelMode
import pl.adrian.planningtripsbackend.trip.model.entity.Trip
import pl.adrian.planningtripsbackend.trip.repository.TripRepository
import pl.adrian.planningtripsbackend.trip.service.TripService
import java.lang.Exception
import java.time.Instant
import java.util.*
import kotlin.jvm.Throws
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import pl.adrian.planningtripsbackend.trip.model.dto.CreateTripDto
import pl.adrian.planningtripsbackend.utils.TokenUtils

@AutoConfigureMockMvc
@WithMockUser(value = "test")
@SpringBootTest(classes = [PlanningTripsBackendApplication::class, TestSecurityConfiguration::class])
class TripControllerTest {

    @Autowired
    private lateinit var restTripMockMvc: MockMvc

    @Autowired
    private lateinit var tripRepository: TripRepository

    @Autowired
    private lateinit var tripService: TripService

    @Autowired
    private lateinit var tripMapper: TripMapper

    private lateinit var trip: Trip

    private lateinit var createTripDto: CreateTripDto

    @BeforeEach
    fun setUp() {
        trip = createTrip()
        createTripDto = createTripDto()
    }

    @Test
    @Throws(Exception::class)
    fun createTripTest() {

        TestSecurityContextHolder.getContext().authentication = TokenUtils.getJwtAuthenticationToken()

        val databaseSizeBeforeCreate = tripRepository.findAll().size

        val writeValueAsBytes = ObjectMapper().writeValueAsBytes(createTripDto)

        restTripMockMvc.perform(post("/api/v1/trip")
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValueAsBytes))
            .andExpect(status().isCreated)

        val tripList = tripRepository.findAll()

        assertThat(tripList).hasSize(databaseSizeBeforeCreate + 1)

        val testTrip = tripList[tripList.size - 1]

        assertThat(testTrip.estimatedLength).isEqualTo(DEFAULT_ESTIMATED_LENGTH)
        assertThat(testTrip.estimatedTime).isEqualTo(DEFAULT_ESTIMATED_TIME)
        assertThat(testTrip.route).isEqualTo(DEFAULT_ROUTE)
        assertThat(testTrip.markers).hasSize(DEFAULT_MARKERS.size)
        assertThat(testTrip.travelMode).isEqualTo(DEFAULT_TRAVEL_MODE)
    }

    @Test
    @Throws(Exception::class)
    fun createTripWithNotEnoughMarkers() {

        TestSecurityContextHolder.getContext().authentication = TokenUtils.getJwtAuthenticationToken()

        val databaseSizeBeforeCreate = tripRepository.findAll().size

        val badTrip = createTripDto.copy(markers = createTripDto.markers.dropLast(1))

        val writeValueAsBytes = ObjectMapper().writeValueAsBytes(badTrip)

        restTripMockMvc.perform(post("/api/v1/trip")
            .contentType(MediaType.APPLICATION_JSON)
            .content(writeValueAsBytes))
            .andExpect(status().is4xxClientError)


        val tripList = tripRepository.findAll()

        assertThat(tripList).hasSize(databaseSizeBeforeCreate)
    }

    @Test
    @Throws(Exception::class)
    fun testCreateTripToTrip() {
        val mappedTrip = tripMapper.toTrip(createTripDto)

        assertNotNull(mappedTrip)
        assertThat(mappedTrip.estimatedLength).isEqualTo(DEFAULT_ESTIMATED_LENGTH)
        assertThat(mappedTrip.estimatedTime).isEqualTo(DEFAULT_ESTIMATED_TIME)
        assertThat(mappedTrip.route).isEqualTo(DEFAULT_ROUTE)
        assertThat(mappedTrip.markers).hasSize(DEFAULT_MARKERS.size)
        assertThat(mappedTrip.travelMode).isEqualTo(DEFAULT_TRAVEL_MODE)
    }

    @Test
    @Throws(Exception::class)
    fun testTripToTripDto() {
        val mappedTrip = tripMapper.toTripDto(trip)

        assertNotNull(mappedTrip)
        assertThat(mappedTrip.id).isEqualTo(DEFAULT_ID)
        assertThat(mappedTrip.createdDate).isEqualTo(DEFAULT_CREATE_DATE)
        assertThat(mappedTrip.estimatedLength).isEqualTo(DEFAULT_ESTIMATED_LENGTH)
        assertThat(mappedTrip.estimatedTime).isEqualTo(DEFAULT_ESTIMATED_TIME)
        assertThat(mappedTrip.route).isEqualTo(DEFAULT_ROUTE)
        assertThat(mappedTrip.markers).hasSize(DEFAULT_MARKERS.size)
        assertThat(mappedTrip.travelMode).isEqualTo(DEFAULT_TRAVEL_MODE)
    }

    companion object {

        private const val DEFAULT_ID = "id1"

        private val DEFAULT_CREATE_DATE = Instant.now()

        private const val DEFAULT_ROUTE = "{route: \"test\"}"

        private const val DEFAULT_ADDED_BY_USER_ID = "user_id1"

        private const val DEFAULT_ESTIMATED_TIME = 1.0

        private const val DEFAULT_ESTIMATED_LENGTH = 1.0

        private val DEFAULT_TRAVEL_MODE = TravelMode.WALKING

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
                    longitude = DEFAULT_MARKER_LNG)
            ),
            Marker(
                id = DEFAULT_MARKER_2_ID,
                title = DEFAULT_MARKER_TITLE,
                position = Position(
                    latitude = DEFAULT_MARKER_LAT,
                    longitude = DEFAULT_MARKER_LNG)
            ))

        fun createTrip(): Trip = Trip(
            id = DEFAULT_ID,
            createdDate = DEFAULT_CREATE_DATE,
            route = DEFAULT_ROUTE,
            addedByUserId = DEFAULT_ADDED_BY_USER_ID,
            estimatedTime = DEFAULT_ESTIMATED_TIME,
            estimatedLength = DEFAULT_ESTIMATED_LENGTH,
            travelMode = DEFAULT_TRAVEL_MODE,
            markers = DEFAULT_MARKERS)

        fun createTripDto(): CreateTripDto = CreateTripDto(
            route = DEFAULT_ROUTE,
            estimatedTime = DEFAULT_ESTIMATED_TIME,
            estimatedLength = DEFAULT_ESTIMATED_LENGTH,
            travelMode = DEFAULT_TRAVEL_MODE,
            markers = DEFAULT_MARKERS)
    }
}