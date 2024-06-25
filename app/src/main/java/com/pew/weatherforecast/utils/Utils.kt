package com.pew.weatherforecast.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.DateTimeException

@RequiresApi(Build.VERSION_CODES.O)
fun dateFormat(date: Int): String? {
  try {
      val date = java.util.Date(date.toLong() * 1000)
      val dateFormatter = SimpleDateFormat("EEE, d MMM")
      val dayFormatter = SimpleDateFormat("EEE")

      return dateFormatter.format(date)
  }
  catch (e: DateTimeException) {
      Log.d("Exception","Parse date error !!"+" ${e.localizedMessage}")
      return null
  }
}



@RequiresApi(Build.VERSION_CODES.O)
fun timeFormat(time: Int): String? {
    try {
        val date = java.util.Date(time.toLong() * 1000)
        val formatter = SimpleDateFormat("hh:mm:aa")
        return formatter.format(date)
    }
    catch (e: DateTimeException) {
        Log.d("Exception","Parse date error !!"+" ${e.localizedMessage}")
        return null
    }
}

fun temperatureFormat(temp: Double) : String {
    return "%.0f".format(temp)
}