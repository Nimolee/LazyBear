package app.lazybear.module.ui.components.animations

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavBackStackEntry

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideFromBottomAnimation(): EnterTransition {
    return slideIntoContainer(
        AnimatedContentTransitionScope.SlideDirection.Up,
        animationSpec = tween(300, easing = EaseIn),
        initialOffset = { (it * 0.2).toInt() }
    ) + fadeIn(
        animationSpec = tween(300, easing = EaseIn),
    )
}