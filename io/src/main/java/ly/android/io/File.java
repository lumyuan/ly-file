package ly.android.io;

import java.io.IOException;
import java.util.Arrays;

import ly.android.io.base.FileApi;
import ly.android.io.base.FileList;
import ly.android.io.base.SimpleDocumentFile;
import ly.android.io.base.SimpleFile;
import ly.android.io.util.DocumentUtil;

public class File implements FileApi, FileList {

    private SimpleFile javaFile;
    private SimpleDocumentFile documentFile;
    private final boolean isPreviewDir;

    public File(String path){
        isPreviewDir = DocumentUtil.isPreviewDir(path);
        if (isPreviewDir){
            documentFile = new SimpleDocumentFile(path);
        }else {
            javaFile = new SimpleFile(path);
        }
    }

    @Override
    public boolean exists() {
        return isPreviewDir ? documentFile.exists() : javaFile.exists();
    }

    @Override
    public String getName() {
        return isPreviewDir ? documentFile.getName() : javaFile.getName();
    }

    @Override
    public String getParent() {
        return isPreviewDir ? documentFile.getParent() : javaFile.getParent();
    }

    @Override
    public String getPath() {
        return isPreviewDir ? documentFile.getPath() : javaFile.getPath();
    }

    @Override
    public boolean canRead() {
        return isPreviewDir ? documentFile.canRead() : javaFile.canRead();
    }

    @Override
    public boolean canWrite() {
        return isPreviewDir ? documentFile.canWrite() : javaFile.canWrite();
    }

    @Override
    public boolean isDirectory() {
        return isPreviewDir ? documentFile.isDirectory() : javaFile.isDirectory();
    }

    @Override
    public boolean isFile() {
        return isPreviewDir ? documentFile.isFile() : javaFile.isFile();
    }

    @Override
    public long lastModified() {
        return isPreviewDir ? documentFile.lastModified() : javaFile.lastModified();
    }

    @Override
    public long length() {
        return isPreviewDir ? documentFile.length() : javaFile.length();
    }

    @Override
    public boolean createNewFile() throws IOException {
        return isPreviewDir ? documentFile.createNewFile() : javaFile.createNewFile();
    }

    @Override
    public boolean delete() {
        return isPreviewDir ? documentFile.delete() : javaFile.delete();
    }

    @Override
    public String[] list() {
        return isPreviewDir ? documentFile.list() : javaFile.list();
    }

    @Override
    public File[] listFile() {
        final String[] listString = list();
        if (listString == null){
            return null;
        }
        final int length = listString.length;
        final File[] files = new File[length];
        for (int i = 0; i < length; i++) {
            files[i] = new File(listString[i]);
        }
        return files;
    }

    @Override
    public boolean mkdirs() {
        return isPreviewDir ? documentFile.mkdirs() : javaFile.mkdirs();
    }

    @Override
    public boolean renameTo(String dest) {
        return isPreviewDir ? documentFile.renameTo(dest) : javaFile.renameTo(dest);
    }

}
