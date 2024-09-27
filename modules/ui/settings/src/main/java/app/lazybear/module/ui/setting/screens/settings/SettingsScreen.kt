package app.lazybear.module.ui.setting.screens.settings

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.lazybear.module.ui.components.buttons.BackButton
import app.lazybear.module.ui.components.cards.SettingsActionCard
import app.lazybear.module.ui.components.cards.SettingsValueCard
import app.lazybear.module.ui.components.helpers.findActivity
import app.lazybear.module.ui.localization.Localization
import app.lazybear.module.ui.setting.R
import app.lazybear.module.ui.setting.components.TMDBNotice
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    arguments: SettingsArguments,
    navigator: SettingsNavigator,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    BackHandler {
        navigator.close()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    BackButton {
                        navigator.close()
                    }
                },
                title = {
                    Text(stringResource(Localization.profile_title))
                },
            )
        },
    ) { insets ->
        Column(
            modifier = Modifier
                .padding(insets)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            val context = LocalContext.current

            SettingsValueCard(
                title = stringResource(Localization.language_title),
                value = stringResource(Localization.current_language_title),
                onClick = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    {
                        val intent = Intent(Settings.ACTION_APP_LOCALE_SETTINGS)
                        intent.data =
                            Uri.fromParts(
                                "package",
                                context.findActivity().packageName,
                                null,
                            )
                        context.startActivity(intent)
                    }
                } else {
                    null
                },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(16.dp))
            SettingsActionCard(
                title = stringResource(Localization.rate_us_title),
                painter = painterResource(R.drawable.ic_open_in_new_24)
            ) {
                Toast.makeText(context, "⭐", Toast.LENGTH_SHORT).show()
            }
            Spacer(Modifier.weight(1f))
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ){
                Image(
                    painter = painterResource(R.drawable.img_tmdb_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .height(64.dp)
                        .padding(end = 16.dp)
                )
                TMDBNotice(modifier = Modifier.weight(1f))
            }
        }
    }
}