package ly.android.explorer.util

import android.widget.Toast
import androidx.annotation.StringRes
import ly.android.explorer.Application

object ToastUtils {

    fun toast(charSequence: CharSequence){
        toast(charSequence, Toast.LENGTH_SHORT)
    }

    fun toast(@StringRes id: Int){
        toast(id, Toast.LENGTH_SHORT)
    }

    fun toast(charSequence: CharSequence, time: Int){
        Toast.makeText(Application.application, charSequence, time).show()
    }

    fun toast(@StringRes id: Int, time: Int){
        Toast.makeText(Application.application, id, time).show()
    }

}