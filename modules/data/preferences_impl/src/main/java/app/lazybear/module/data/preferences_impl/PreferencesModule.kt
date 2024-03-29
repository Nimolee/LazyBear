package app.lazybear.module.data.preferences_impl

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import app.lazybear.module.data.preferences.PreferencesRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun preferencesModule(
    tokenFlowName: String,
) = module {
    single(named(BuildConfig.SHARED_PREFERENCES_NAME)) {
        get<Context>().getSharedPreferences(BuildConfig.SHARED_PREFERENCES_NAME, MODE_PRIVATE)
    }
    single(named(BuildConfig.ENCRYPTED_SHARED_PREFERENCES_NAME)) {
        val masterKey = MasterKey.Builder(get<Context>())
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        EncryptedSharedPreferences.create(
            get<Context>(),
            BuildConfig.ENCRYPTED_SHARED_PREFERENCES_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    single<PreferencesRepository> {
        PreferencesRepositoryImpl(
            get(named(BuildConfig.SHARED_PREFERENCES_NAME)),
            get(named(BuildConfig.ENCRYPTED_SHARED_PREFERENCES_NAME)),
        )
    }
    factory(named(tokenFlowName)) {
        get<PreferencesRepository>().tokenFlow
    }
}