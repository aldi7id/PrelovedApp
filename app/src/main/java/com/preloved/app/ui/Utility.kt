package com.preloved.app.ui

import android.content.ContentResolver
import android.content.Context
import android.graphics.Paint
import android.net.Uri
import android.os.Environment
import android.widget.TextView
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

val listCategory : MutableList<String> = ArrayList()
val listCategoryId : MutableList<Int> = ArrayList()

private const val FILENAME_FORMAT = "dd-MMM-yyyy"
private val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())
private fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}
fun currency(angka: Int): String {
    val kursIndonesia = DecimalFormat.getCurrencyInstance() as DecimalFormat
    val formatRp = DecimalFormatSymbols()

    formatRp.currencySymbol = "Rp "
    formatRp.monetaryDecimalSeparator = ','
    formatRp.groupingSeparator = '.'

    kursIndonesia.decimalFormatSymbols = formatRp
    return kursIndonesia.format(angka).dropLast(3)
}
fun uriToFile(
    selectedImage: Uri,
    context: Context,
): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImage) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) {
        outputStream.write(buf, 0, len)
    }
    outputStream.close()
    inputStream.close()

    return myFile
}
fun striketroughtText(tv: TextView, textChange: String): String {
    tv.paintFlags = tv.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    return textChange
}
fun convertDate(date: String): String {
    var kotlin = date
    kotlin = kotlin.drop(5)
    var bulan = kotlin.take(2)
    kotlin = kotlin.drop(3)
    val tanggal = kotlin.take(2)
    kotlin = kotlin.drop(3)
    val jam = kotlin.take(2)
    kotlin = kotlin.drop(3)
    val menit = kotlin.take(2)

    when (bulan) {
        "01" -> {
            bulan = "Jan"
        }
        "02" -> {
            bulan = "Feb"
        }
        "03" -> {
            bulan = "Mar"
        }
        "04" -> {
            bulan = "Apr"
        }
        "05" -> {
            bulan = "Mei"
        }
        "06" -> {
            bulan = "Jun"
        }
        "07" -> {
            bulan = "Jul"
        }
        "08" -> {
            bulan = "Agu"
        }
        "09" -> {
            bulan = "Sep"
        }
        "10" -> {
            bulan = "Okt"
        }
        "11" -> {
            bulan = "Nov"
        }
        "12" -> {
            bulan = "Des"
        }
    }

    return "$tanggal $bulan, $jam:$menit"


}