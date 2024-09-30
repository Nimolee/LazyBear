package app.lazybear.module.ui.filters.helpers

import app.lazybear.module.ui.filters.R
import app.lazybear.module.ui.localization.Localization
import com.lazybear.module.data.tmdb_api.entities.Genre as DomainGenre

private enum class Genre {
    action,
    adventure,
    animation,
    comedy,
    crime,
    documentary,
    drama,
    family,
    fantasy,
    history,
    horror,
    music,
    mystery,
    romance,
    scienceFiction,
    tv,
    thriller,
    war,
    western,
    unknown,
}

private fun DomainGenre.toEnum(): Genre {
    return when (id) {
        28 -> Genre.action
        12 -> Genre.adventure
        16 -> Genre.animation
        35 -> Genre.comedy
        80 -> Genre.crime
        99 -> Genre.documentary
        18 -> Genre.drama
        10751 -> Genre.family
        14 -> Genre.fantasy
        36 -> Genre.history
        27 -> Genre.horror
        10402 -> Genre.music
        9648 -> Genre.mystery
        10749 -> Genre.romance
        878 -> Genre.scienceFiction
        10770 -> Genre.tv
        53 -> Genre.thriller
        10752 -> Genre.war
        37 -> Genre.western
        else -> Genre.unknown
    }
}

fun List<DomainGenre>.filtered(): List<DomainGenre> {
    return this.filter { it.toEnum() != Genre.unknown }
}

val DomainGenre.iconRes: Int
    get() {
        return when (this.toEnum()) {
            Genre.action -> R.drawable.ic_action
            Genre.adventure -> R.drawable.ic_adventure
            Genre.animation -> R.drawable.ic_filter_placeholder //TODO: Update
            Genre.comedy -> R.drawable.ic_comedy
            Genre.crime -> R.drawable.ic_crime
            Genre.documentary -> R.drawable.ic_documentary
            Genre.drama -> R.drawable.ic_drama
            Genre.family -> R.drawable.ic_family
            Genre.fantasy -> R.drawable.ic_fantasy
            Genre.history -> R.drawable.ic_history
            Genre.horror -> R.drawable.ic_horror
            Genre.music -> R.drawable.ic_music
            Genre.mystery -> R.drawable.ic_mystey
            Genre.romance -> R.drawable.ic_romance
            Genre.scienceFiction -> R.drawable.ic_sci_fi
            Genre.tv -> R.drawable.ic_filter_placeholder //TODO: Update
            Genre.thriller -> R.drawable.ic_thriller
            Genre.war -> R.drawable.ic_war
            Genre.western -> R.drawable.ic_western
            Genre.unknown -> R.drawable.ic_filter_placeholder
        }
    }

val DomainGenre.nameRes: Int
    get() {
        return when (this.toEnum()) {
            Genre.action -> Localization.action_filter_title
            Genre.adventure -> Localization.adventure_filter_title
            Genre.animation -> Localization.animation_filter_title
            Genre.comedy -> Localization.comedy_filter_title
            Genre.crime -> Localization.crime_filter_title
            Genre.documentary -> Localization.documentary_filter_title
            Genre.drama -> Localization.drama_filter_title
            Genre.family -> Localization.family_filter_title
            Genre.fantasy -> Localization.fantasy_filter_title
            Genre.history -> Localization.history_filter_title
            Genre.horror -> Localization.horror_filter_title
            Genre.music -> Localization.music_filter_title
            Genre.mystery -> Localization.mystery_filter_title
            Genre.romance -> Localization.romance_filter_title
            Genre.scienceFiction -> Localization.scienceFiction_filter_title
            Genre.tv -> Localization.tv_filter_title
            Genre.thriller -> Localization.thriller_filter_title
            Genre.war -> Localization.war_filter_title
            Genre.western -> Localization.western_filter_title
            Genre.unknown -> Localization.unknown_filter_title
        }
    }
