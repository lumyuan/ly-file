package ly.android.explorer.theme

import android.app.UiModeManager
import android.content.Context
import ly.android.explorer.Application

class Theme {

    companion object {
        fun isDarkMode(): Boolean {
            val uiModeManager: UiModeManager = Application.application.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
            return uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES
        }
    }

}