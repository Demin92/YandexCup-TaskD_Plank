package ru.demin.taskd_plank

private const val SECONDS_IN_MINUTE = 60

fun Int.convertToTime(): String {
    val minutes = this / SECONDS_IN_MINUTE
    val seconds = this % SECONDS_IN_MINUTE
    return "$minutes:$seconds"
}