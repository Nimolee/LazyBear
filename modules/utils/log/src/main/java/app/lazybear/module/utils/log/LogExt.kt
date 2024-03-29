package app.lazybear.module.utils.log

fun logD(tag: String, method: String, messageBuilder: () -> String) {
    LogHelper.log(tag, method, messageBuilder)
}