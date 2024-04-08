package com.lazybear.module.data.tmdb_api_impl.entities

import com.lazybear.module.data.tmdb_api.entities.ReleaseYear
import java.time.LocalDateTime

data class ReleaseYearEntity(
    val start: LocalDateTime,
    val end: LocalDateTime,
) {
    companion object {
        private const val DECADE_DURATION = 10L
        private const val LAST_N_YEARS = 2L


        /**
         * Compose release years array when first value is last [LAST_N_YEARS]
         * and then groups of [DECADE_DURATION] years
         */
        fun getYears(): List<ReleaseYearEntity> {
            val releaseYears = mutableListOf<ReleaseYearEntity>()
            var startDate = LocalDateTime.of(1900, 1, 1, 0, 0)
            var endDate = startDate
            val now = LocalDateTime.now()
            //80-s and prior
            releaseYears.add(
                ReleaseYearEntity(
                    start = startDate.withYear(1900),
                    end = startDate.withYear(1980)
                )
            )
            startDate = startDate.withYear(1980)
            //Compose decades to now
            while (startDate.isBefore(now)) {
                endDate = startDate.plusYears(DECADE_DURATION)
                if (endDate.isAfter(now)) {
                    endDate = endDate.withYear(now.year + 1)
                }
                releaseYears.add(
                    0,
                    ReleaseYearEntity(
                        start = startDate,
                        end = endDate,
                    )
                )
                startDate = startDate.plusYears(DECADE_DURATION)
            }
            //add last two years
            releaseYears.add(
                0, ReleaseYearEntity(
                    start = endDate.minusYears(2),
                    end = endDate,
                )
            )
            return releaseYears
        }
    }
}

fun ReleaseYearEntity.toDomain(): ReleaseYear {
    return ReleaseYear(
        start = start,
        end = end,
    )
}

fun ReleaseYear.toEntity(): ReleaseYearEntity {
    return ReleaseYearEntity(
        start = start,
        end = end,
    )
}
