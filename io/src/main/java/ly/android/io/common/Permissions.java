package ly.android.io.common;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.core.app.ActivityCompat;

import ly.android.io.FileApplication;
import ly.android.io.util.DocumentUtil;

public class Permissions {

    public static boolean hasCallAllFile(){
        if (DocumentUtil.atLeastR()){
            return Environment.isExternalStorageManager();
        }
        return true;
    }

    public static void getCallAllFile(){
        Intent intent = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION")
                .setData(Uri.parse("package:" + FileApplication.getApplication().getPackageName()));
        if (DocumentUtil.atLeastR()){
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        FileApplication.getApplication().startActivity(intent);
    }

    public static boolean hasPrevDir(String folderId, String packageName){
        if (DocumentUtil.atLeastR()){
            return DocumentUtil.checkFolderPermission(folderId, packageName);
        }
        return true;
    }

    @Deprecated
    public static boolean hasPrevDir(String folderId){
        return hasPrevDir(folderId, null);
    }

    public static void getPrevDir(Activity activity, int requestCode, String folderId, String packageName){
        DocumentUtil.requestFolderPermission(activity, requestCode, folderId, packageName);
    }

    public static String getDocId(String absolutePath){
        return absolutePath.startsWith(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data") ? DocumentUtil.DOCID_ANDROID_DATA : absolutePath.startsWith(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/obb") ? DocumentUtil.DOCID_ANDROID_OBB : null;
    }

    public static boolean hasPreDir(String absolutePath){
        if (DocumentUtil.atLeastR()){
            final String docId = getDocId(absolutePath);
            final String packageName = DocumentUtil.getPackageName(absolutePath);
            return hasPrevDir(docId, packageName);
        }else {
            return true;
        }
    }

    public static void getPreDir(Activity activity, int requestCode, String absolutePath){
        final String docId = getDocId(absolutePath);
        final String packageName = DocumentUtil.getPackageName(absolutePath);
        getPrevDir(activity, requestCode, docId, packageName);
    }

    public static void getPrevDir(Activity activity, int requestCode, String folderId){
        getPrevDir(activity, requestCode, folderId, null);
    }

    public static void savePermissions(Activity activity, int requestCode, int resultCode, Intent data){
        DocumentUtil.savePermissions(activity, requestCode, resultCode, data);
    }

    private static final String[] PERMISSIONS_STORAGE = {
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static final int REQUEST_PERMISSION_CODE = 202024;

    public static boolean hasCallExternalStorage(){
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || ActivityCompat.checkSelfPermission(FileApplication.getApplication(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static void getCallExternalStorage(Activity activity){
        ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
    }
}
