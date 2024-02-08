package app.lazybear.module.utils.log

fun log(tag: String, method: String, messageBuilder: () -> String) {
    LogHelper.log(tag, method, messageBuilder)
}