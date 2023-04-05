package ly.android.io.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.UriPermission;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;

import androidx.annotation.ChecksSdkIntAtLeast;
import androidx.annotation.RequiresApi;
import androidx.documentfile.provider.DocumentFile;

import java.util.List;
import java.util.Objects;

import ly.android.io.FileApplication;

public class DocumentUtil {

    private static final String dataParent = "/Android/data";
    private static final String obbParent = "/Android/obb";
    public static final String DOC_AUTHORITY = "com.android.externalstorage.documents";

    public static final String DOCID_ANDROID_DATA = "primary:Android/data";
    public static final String DOCID_ANDROID_OBB = "primary:Android/obb";

    public static final int REQ_SAF_R_DATA = 202030;
    public static final int REQ_SAF_R_OBB  = 202036;

    public static final String DOCUMENT_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android";

    public static DocumentFile getTreeDocumentFile(String absolutePath, boolean isCreateDir){
        if (!isPreviewPath(absolutePath)){
            throw new RuntimeException("The path entered does not belong to /Android/data or /Android/obb.");
        }
        String reallyPath = getReallyPath(absolutePath);
        final String pathContent = getPathContent(reallyPath);
        String[] fileItems = pathContent.split("/");
        DocumentFile documentFile = getRootDocumentFile(absolutePath, atLeastTiramisu() ? getPackageName(absolutePath) : null);
        for (int i = atLeastTiramisu() ? 2 : 1; i < fileItems.length; i++) {
            if (documentFile != null){
                final DocumentFile file = documentFile.findFile(fileItems[i]);
                if (file != null){
                    documentFile = file;
                }else {
                    if (isCreateDir){
                        documentFile = documentFile.createFile("*/*", fileItems[i]);
                    }else {
                        return null;
                    }
                }
            }else {
                return null;
            }
        }
        return documentFile;
    }

    public static boolean isPreviewDir(String absolutePath){
        return atLeastR() && isPreviewPath(absolutePath);
    }

    public static String getDocId(String absolutePath){
        if (!isPreviewDir(absolutePath)) {
            return null;
        }
        return absolutePath.startsWith(DOCUMENT_ROOT + "/data") ? DOCID_ANDROID_DATA : DOCID_ANDROID_OBB;
    }

    public static String getPathContent(String absolutePath){
        if (!isPreviewPath(absolutePath)){
            throw new RuntimeException("The path entered does not belong to /Android/data or /Android/obb.");
        }
        absolutePath = absolutePath.substring(absolutePath.indexOf(DOCUMENT_ROOT) + DOCUMENT_ROOT.length());
        return absolutePath.startsWith("/") ? absolutePath.substring(1) : absolutePath;
    }

    public static String getPathContent(String absolutePath, String content){
        if (!isPreviewPath(absolutePath)){
            throw new RuntimeException("The path entered does not belong to /Android/data or /Android/obb.");
        }
        absolutePath = absolutePath.substring(absolutePath.indexOf(DOCUMENT_ROOT + content) + DOCUMENT_ROOT.length() + content.length());
        return absolutePath.startsWith("/") ? absolutePath.substring(1) : absolutePath;
    }

    public static DocumentFile getRootDocumentFile(String absolutePath, String packageName){
        if (isData(absolutePath)) {
            return DocumentFile.fromTreeUri(FileApplication.getApplication(), getFolderUri(DOCID_ANDROID_DATA, packageName, true));
        }else {
            return DocumentFile.fromTreeUri(FileApplication.getApplication(), getFolderUri(DOCID_ANDROID_OBB, packageName, true));
        }
    }

    public static String getPackageName(String absolutePath){
        if (!isPreviewPath(absolutePath)){
            throw new RuntimeException("The path entered does not belong to /Android/data or /Android/obb.");
        }
        String path = absolutePath.replaceAll(DOCUMENT_ROOT + (isData(absolutePath) ? "/data" : "/obb"), "");
        path = path.startsWith("/") ? path.substring(1) : path;
        int endIndex = path.indexOf("/");
        if (endIndex != -1){
            return path.substring(0, endIndex);
        }else  {
            return path;
        }
    }

    public static boolean isData(String absolutePath){
        return absolutePath.replaceAll(DOCUMENT_ROOT, "").startsWith("/data");
    }

    public static String getReallyPath(String absolutePath){
        if (absolutePath.lastIndexOf("/") == absolutePath.length() - 1){
            absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
        }
        return absolutePath;
    }

    private static boolean isPreviewPath(String absolutePath){
        return absolutePath.startsWith(Environment.getExternalStorageDirectory().getAbsolutePath() + dataParent)
                || absolutePath.startsWith(Environment.getExternalStorageDirectory().getAbsolutePath() + obbParent);
    }

    public static Uri getFolderUri(String folderId, String packageName, boolean tree){
        String pn = packageName == null ? "" : "/" + packageName;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return tree ? DocumentsContract.buildTreeDocumentUri(DOC_AUTHORITY, folderId + pn) : DocumentsContract.buildDocumentUri(DOC_AUTHORITY, folderId + pn);
        }
        throw new RuntimeException("Call requires API level 21");
    }

    public static boolean isInPersistedUriPermissions(Uri uri){
        List<UriPermission> persistedUriPermissions = FileApplication.getApplication().getContentResolver().getPersistedUriPermissions();
        for (UriPermission persistedUriPermission : persistedUriPermissions) {
            if (persistedUriPermission.getUri().toString().equals(uri.toString()) && (persistedUriPermission.isReadPermission() || persistedUriPermission.isWritePermission())){
                return true;
            }
        }
        return false;
    }

    public static Uri getRootUri(Uri uri){
        List<UriPermission> persistedUriPermissions = FileApplication.getApplication().getContentResolver().getPersistedUriPermissions();
        for (UriPermission persistedUriPermission : persistedUriPermissions) {
            if (UriUtil.uri2path(persistedUriPermission.getUri().toString()).equals(UriUtil.uri2path(uri.toString())) && (persistedUriPermission.isReadPermission() || persistedUriPermission.isWritePermission())){
                return persistedUriPermission.getUri();
            }
        }
        return null;
    }

    @ChecksSdkIntAtLeast(api = 33)
    public static boolean atLeastTiramisu(){
        return Build.VERSION.SDK_INT >= 33;
    }

    @ChecksSdkIntAtLeast(api = Build.VERSION_CODES.R)
    public static boolean atLeastR(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.R;
    }

    public static boolean checkFolderPermission(String folderId, String packageName){
        if (atLeastR()){
            final Uri treeUri = getFolderUri(folderId, atLeastTiramisu() ? packageName : null, true);
            return isInPersistedUriPermissions(treeUri);
        }else {
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Intent getUriOpenIntent(Uri uri){
        System.out.println(uri);
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
                | Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, Objects.requireNonNull(DocumentFile.fromTreeUri(FileApplication.getApplication(), uri)).getUri());
        return intent;
    }

    public static void requestFolderPermission(Activity activity, int requestCode, String folderId, String packageName){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = getUriOpenIntent(getFolderUri(folderId, atLeastTiramisu() ? packageName : null, true));
            intent.putExtra("pn", packageName);
            activity.startActivityForResult(intent, requestCode);
        }
    }

    @SuppressLint("WrongConstant")
    public static void savePermissions(Activity activity, int requestCode, int resultCode, Intent data){
        if (data != null){
            switch (requestCode) {
                case REQ_SAF_R_DATA:
                    if (!checkFolderPermission(DOCID_ANDROID_DATA, data.getStringExtra("pn"))){
                        if (resultCode == Activity.RESULT_OK){
                            activity.getContentResolver().takePersistableUriPermission(data.getData(), data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION));
                        }
                    }
                    break;
                case REQ_SAF_R_OBB:
                    if (!checkFolderPermission(DOCID_ANDROID_OBB, data.getStringExtra("pn"))){
                        if (resultCode == Activity.RESULT_OK){
                            activity.getContentResolver().takePersistableUriPermission(data.getData(), data.getFlags() & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION));
                        }
                    }
                    break;
            }
        }
    }
}
