package lou.sizzo.adsadmobkotlin.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import lou.sizzo.adsadmobkotlin.databinding.SplashBinding

class Dialogs {

    fun progressDialog(context: Context): Dialog{
        val dialog = Dialog(context)
        var bindingPDialog: SplashBinding = SplashBinding.inflate(
            LayoutInflater.from(context))
        dialog.setContentView(bindingPDialog.root)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}