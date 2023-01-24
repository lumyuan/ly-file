package ly.android.io.util;

import android.net.Uri;
import android.os.Environment;

public class UriUtil {

    /**
     * Convert absolute path to Android Uri.
     * @param path absolute path
     * @return android Uri
     */
    public static Uri path2UriString(String path){
        final String dataPath = path.replaceAll("/storage/emulated/0/Android/data", "");
        final String uri = Uri.encode(dataPath).toString();
        return Uri.parse("content://com.android.externalstorage.documents/tree/primary%3AAndroid%2Fdata/document/primary%3AAndroid%2Fdata" + uri);
    }


    /**
     * Convert Android Content Uri to path.
     * @param uri android Uri
     * @return absolutePath
     */
    public static String uri2path(String uri){
        final String uriBody = uri.substring(0, uri.lastIndexOf("%3A") + 3);
        String uriContent = uri.replaceAll(uriBody, "");
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Uri.decode(uriContent);
    }
}
