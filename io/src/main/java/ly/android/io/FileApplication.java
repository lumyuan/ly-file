package ly.android.io;

import android.content.Context;

public class FileApplication {

    private static Context application;

    public static void init(Context context){
        application = context;
    }

    public static Context getApplication(){
        if (application == null){
            throw new NullPointerException("Please call init(Context) function.");
        }
        return application;
    }

}
