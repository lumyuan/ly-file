package ly.android.io;

import android.content.Context;

public class FileApplication {

    public static Context application;

    public static void init(Context context){
        application = context;
    }

}
