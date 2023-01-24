package ly.android.io.media;

import androidx.annotation.NonNull;

import java.util.Locale;

public class MediaType {

    @NonNull
    public static Type getFileType(String path){
        int lastIndexOf = path.lastIndexOf("/");
        if (lastIndexOf == -1){
            return Type.UNKNOWN;
        }
        final String name = path.substring(lastIndexOf + 1);
        int typePoint = name.lastIndexOf(".");
        if (typePoint == -1){
            return Type.UNKNOWN;
        }
        final String type = name.substring(typePoint + 1).toLowerCase(Locale.ROOT);
        switch (type) {
            //text file
            case "txt":
            case "ini":
                return Type.TEXT;
            //image file
            case "png":
            case "jpg":
            case "jpeg":
            case "gif":
                return Type.IMAGE;
            //video file
            case "mp4":
                return Type.VIDEO;
            //zip file
            case "zip":
            case "aar":
            case "rar":
            case "7z":
                return Type.ZIP;
            case "apk":
                return Type.APP;
            default:
                return Type.UNKNOWN;
        }
    }

    public enum Type {
        UNKNOWN,
        TEXT,
        IMAGE,
        VIDEO,
        ZIP,
        DOC,
        CODE,
        APP
    }
}
