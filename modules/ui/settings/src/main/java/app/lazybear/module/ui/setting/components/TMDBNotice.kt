package app.lazybear.module.ui.setting.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import app.lazybear.module.ui.localization.Localization

@Composable
fun TMDBNotice(
    modifier: Modifier,
) {
    val annotatedString = buildAnnotatedString {
        val notice = stringResource(id = Localization.tmdb_notice)
        val link = stringResource(id = Localization.tmdb_link)

        val startIndex = notice.length + 1
        val endIndex = startIndex + link.length
        append(notice)
        append(" ")
        withLink(LinkAnnotation.Url(url = link)) {
            append(link)
        }
        addStyle(
            style = MaterialTheme.typography.bodySmall
                .toSpanStyle(),
            start = 0,
            end = startIndex,
        )
        addStyle(
            style = MaterialTheme.typography.bodySmall
                .copy(color = MaterialTheme.colorScheme.primary)
                .toSpanStyle(),
            start = startIndex,
            end = endIndex,
        )
    }
    return Text(
        text = annotatedString,
        style = MaterialTheme.typography.bodySmall,
        modifier = modifier,
    )
}