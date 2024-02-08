package app.lazybear.module.utils.log

import android.util.Log

object LogHelper {
    fun log(tag: String, method: String, messageBuilder: () -> String) {
        Log.d(tag, "$method-> ${messageBuilder()}")
    }
}