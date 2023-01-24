package ly.android.io.common;

import android.os.Build;

import androidx.documentfile.provider.DocumentFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import ly.android.io.File;
import ly.android.io.FileApplication;
import ly.android.io.util.DocumentUtil;

public class IOUtils {

    public static InputStream openInputStream(String path) throws IOException {
        if (DocumentUtil.isPreviewDir(path)){
            final DocumentFile treeDocumentFile = DocumentUtil.getTreeDocumentFile(path, false);
            if (treeDocumentFile == null){
                throw new IOException("No such file or directory.");
            }
            return FileApplication.application.getContentResolver().openInputStream(treeDocumentFile.getUri());
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Files.newInputStream(Paths.get(path));
            }else {
                return new FileInputStream(path);
            }
        }
    }

    public static OutputStream openOutputStream(String path) throws IOException {
        if (DocumentUtil.isPreviewDir(path)){
            final DocumentFile treeDocumentFile = DocumentUtil.getTreeDocumentFile(path, false);
            if (treeDocumentFile == null){
                throw new IOException("No such file or directory.");
            }
            return FileApplication.application.getContentResolver().openOutputStream(treeDocumentFile.getUri(), "rwt");
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Files.newOutputStream(Paths.get(path));
            }else {
                return new FileOutputStream(path);
            }
        }
    }

    public static byte[] readBytes(InputStream inputStream) throws IOException {
        final BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len;
        int length = bytes.length;
        while ((len = bufferedInputStream.read(bytes, 0, length)) != -1){
            byteArrayOutputStream.write(bytes, 0, len);
        }
        final byte[] data = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        bufferedInputStream.close();
        inputStream.close();
        return data;
    }

    public static byte[] readBytes(String path) throws IOException {
        return readBytes(openInputStream(path));
    }

    public static void writeBytes(OutputStream outputStream, byte[] data) throws IOException {
        final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        bufferedOutputStream.write(data, 0, data.length);
        bufferedOutputStream.close();
        outputStream.close();
    }

    public static void writeBytes(String path, byte[] data) throws IOException {
        writeBytes(openOutputStream(path), data);
    }

    private static void copyFile(String oldPath, String newPath, boolean delete) throws IOException {
        final byte[] bytes = readBytes(oldPath);
        writeBytes(newPath, bytes);
        if (delete){
            new File(oldPath).delete();
        }
    }

    public static void copyFile(String oldPath, String newPath) throws IOException {
        copyFile(oldPath, newPath, false);
    }

    public static void cutFile(String oldPath, String newPath) throws IOException {
        copyFile(oldPath, newPath, true);
    }
}
