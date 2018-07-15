package com.orai.oraitest.helper

import java.text.SimpleDateFormat
import java.util.*

object DateConverter{

    private const val MAIN_PAGE_DATE_FORMAT_PATTERN = "dd.MM.yyyy, EEEE, HH:mm"
    private fun appDateFormat() = SimpleDateFormat(MAIN_PAGE_DATE_FORMAT_PATTERN)


    fun convertTimestampIntoData(timestampInSeconds: Long) = appDateFormat().format(
            Date(
                    convertSecondsIntoMilli(timestampInSeconds)
            )
    )!!

    private fun convertSecondsIntoMilli(timestampInSeconds: Long) = 1000 * timestampInSeconds

}