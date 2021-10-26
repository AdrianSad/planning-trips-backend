package pl.adrian.planningtripsbackend.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import pl.adrian.planningtripsbackend.user.model.entity.Gender

class CaloriesUtilsTest {

    @Test
    fun getBurnedCalories() {

        assertEquals(
            420.3097476192953, CaloriesUtils.calculateEnergyExpenditure(
                DEFAULT_HEIGHT,
                DEFAULT_AGE,
                DEFAULT_WEIGHT,
                DEFAULT_DURATION,
                DEFAULT_LENGTH,
                DEFAULT_GENDER
            )
        )


        assertEquals(
            1.7361111111111112, CaloriesUtils.convertKilocaloriesToMlKmin(
                DEFAULT_KILOCALORIES,
                DEFAULT_WEIGHT
            )
        )
    }

    companion object {
        private const val DEFAULT_HEIGHT = 180.0

        private const val DEFAULT_AGE = 22

        private const val DEFAULT_WEIGHT = 80.0

        private const val DEFAULT_DURATION = 1.0

        private const val DEFAULT_LENGTH = 4.0

        private val DEFAULT_GENDER = Gender.MALE

        private const val DEFAULT_KILOCALORIES = 1000.0
    }
}