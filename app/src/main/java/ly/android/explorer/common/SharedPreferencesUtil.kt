package ly.android.explorer.common

import android.content.Context
import ly.android.explorer.Application

object SharedPreferencesUtil {

    fun save(key: String?, value: String?): Boolean{
        val sharedPreferences =
            Application.application.getSharedPreferences(
                Application.application.packageName,
                Context.MODE_PRIVATE
            )
        return sharedPreferences.edit().putString(key, value).commit()
    }

    fun load(key: String?): String?{
        val sharedPreferences =
            Application.application.getSharedPreferences(
                Application.application.packageName,
                Context.MODE_PRIVATE
            )
        return sharedPreferences.getString(key, null)
    }

}