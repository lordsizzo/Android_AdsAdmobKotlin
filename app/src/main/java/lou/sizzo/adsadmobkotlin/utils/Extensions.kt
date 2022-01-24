package lou.sizzo.adsadmobkotlin.utils

import android.content.Context
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar

fun Context.toast(message: String, length: Int = Toast.LENGTH_LONG){
    Toast.makeText(this, message, length).show()
}

fun View.snackbar(message: String, length: Int){
    Snackbar.make(this, message, length).show()
}

