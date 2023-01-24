package ly.android.explorer

import ly.android.io.FileApplication

class Application : android.app.Application() {

    companion object {
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this

        //初始化File
        FileApplication.init(application)
    }

}