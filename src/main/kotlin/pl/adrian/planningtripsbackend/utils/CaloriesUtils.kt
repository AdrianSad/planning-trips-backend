package pl.adrian.planningtripsbackend.utils

class CaloriesUtils {

    companion object {
        fun calculateEnergyExpenditure(
            height: Double,
            age: Int,
            weight: Double,
            gender: Int,
            durationInHours: Double,
            kmTraveled: Double,

            ): Double {
            val harrisBenedictRmR: Double = convertKilocaloriesToMlKmin(
                harrisBenedictRmr(
                    gender,
                    weight,
                    age,
                    height
                ), weight
            )
            val speedInMph: Double = kmTraveled / durationInHours
            val metValue: Double = getMetForActivity(speedInMph)
            val constant = 3.0
            val correctedMets = metValue * (constant / harrisBenedictRmR)
            return correctedMets * durationInHours * weight
        }

        fun convertKilocaloriesToMlKmin(kilocalories: Double, weightKgs: Double): Double {
            var kcalMin = kilocalories / 1440
            kcalMin /= 5f
            return kcalMin / weightKgs * 1000
        }

        private fun harrisBenedictRmr(gender: Int, weightKg: Double, age: Int, heightCm: Double): Double {
            return if (gender == 1) {
                655.0955f + 1.8496f * heightCm + 9.5634f * weightKg - 4.6756f * age
            } else {
                66.4730f + 5.0033f * heightCm + 13.7516f * weightKg - 6.7550f * age
            }
        }

        private fun getMetForActivity(speedInMph: Double): Double {
            if (speedInMph < 2.0) {
                return 2.0
            } else if (speedInMph.compareTo(2.0) == 0) {
                return 2.8
            } else if (speedInMph.compareTo(2.0) > 0 && speedInMph.compareTo(2.7) <= 0
            ) {
                return 3.0
            } else if (speedInMph.compareTo(2.8) > 0 && speedInMph.compareTo(3.3) <= 0
            ) {
                return 3.5
            } else if (speedInMph.compareTo(3.4) > 0 && speedInMph.compareTo(3.5) <= 0
            ) {
                return 4.3
            } else if (speedInMph.compareTo(3.5) > 0 && speedInMph.compareTo(4.0) <= 0
            ) {
                return 5.0
            } else if (speedInMph.compareTo(4.0) > 0 && speedInMph.compareTo(4.5) <= 0
            ) {
                return 7.0
            } else if (speedInMph.compareTo(4.5) > 0 && speedInMph.compareTo(5.0) <= 0
            ) {
                return 8.3
            } else if (speedInMph.compareTo(5.0) > 0) {
                return 9.8
            }
            return 0.0
        }
    }
}