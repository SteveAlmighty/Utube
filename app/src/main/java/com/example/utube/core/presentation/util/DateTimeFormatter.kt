package com.example.utube.core.presentation.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatIso8601Time(iso8601String: String, outputFormat: String = "MMM dd, yyyy 'at' HH:mm"): String? {
    return try {
        // Parse the ISO 8601 string to a ZonedDateTime
        val zonedDateTime = ZonedDateTime.parse(iso8601String)

        // Format the ZonedDateTime to the desired output format
        val formatter = DateTimeFormatter.ofPattern(outputFormat, Locale.getDefault())
        zonedDateTime.format(formatter)
    } catch (e: Exception) {
        // Handle parsing errors (e.g., invalid format)
        println("Error formatting time: ${e.message}")
        null
    }
}