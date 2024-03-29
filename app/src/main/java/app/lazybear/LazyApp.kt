package app.lazybear

import android.app.Application
import app.lazybear.module.data.preferences_impl.preferencesModule
import com.lazybear.module.data.tmdb_api_impl.tmdbModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class LazyApp : Application() {
    companion object {
        const val TOKEN_FLOW_NAME = "token_flow"
    }

    override fun onCreate() {

        super.onCreate()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.NONE)
            androidContext(applicationContext)
            modules(
                preferencesModule(TOKEN_FLOW_NAME),
                tmdbModule(TOKEN_FLOW_NAME, BuildConfig.TMDB_API_KEY),
                mainModule()
            )
        }
    }
}