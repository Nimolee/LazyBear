package app.lazybear.module.ui.advice.screens.advice

import app.lazybear.module.ui.navigation.NavResult

interface AdviceNavigator {
    val settingsResult: NavResult<Boolean>

    fun openSettings()

    fun openBackdropGallery(movieId: Int)
}