package app.lazybear.module.ui.components.animations

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavBackStackEntry

fun AnimatedContentTransitionScope<NavBackStackEntry>.slideToBottomAnimation(): ExitTransition {
    return slideOutOfContainer(
        AnimatedContentTransitionScope.SlideDirection.Down,
        animationSpec = tween(300, easing = EaseIn),
        targetOffset = { (it * 0.2).toInt() }
    ) + fadeOut(
        animationSpec = tween(300, easing = EaseIn),
    )
}