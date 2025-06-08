package com.xsquare.documentscanner.utils

import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Created by Rajath on 06/04/25.
 */

fun Long.timestampToDate(): String {
    val formatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val stamp = Timestamp(this)
    val date = Date(stamp.time)
    return formatter.format(date)
}