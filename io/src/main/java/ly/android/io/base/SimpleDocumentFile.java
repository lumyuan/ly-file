package ly.android.io.base;

import android.net.Uri;

import androidx.documentfile.provider.DocumentFile;

import java.io.File;
import java.io.IOException;

import ly.android.io.FileApplication;
import ly.android.io.util.DocumentUtil;
import ly.android.io.util.UriUtil;

public final class SimpleDocumentFile implements FileApi {

    private DocumentFile file;
    private final String path;

    private final File javaFile;

    public SimpleDocumentFile(String path){
        this.path = path;
        javaFile = new File(path);
    }

    private DocumentFile getFile() {
        if (this.file == null) {
            this.file = DocumentUtil.getTreeDocumentFile(path, false);
        }
        return this.file;
    }

    @Override
    public boolean exists() {
        DocumentFile f = getFile();
        return f != null && f.exists();
    }

    @Override
    public String getName() {
        return javaFile.getName();
    }

    @Override
    public String getParent() {
//        if (this.file != null){
//            final DocumentFile parentFile = getFile().getParentFile();
//            if (parentFile != null){
//                return UriUtil.uri2path(parentFile.getUri().toString());
//            }else {
//                return null;
//            }
//        }else {
//            return null;
//        }
        return javaFile.getParent();
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public boolean canRead() {
        DocumentFile f = getFile();
        return f != null && f.canRead();
    }

    @Override
    public boolean canWrite() {
        DocumentFile f = getFile();
        return f != null && f.canWrite();
    }

    @Override
    public boolean isDirectory() {
        DocumentFile f = getFile();
        return f != null && f.isDirectory();
    }

    @Override
    public boolean isFile() {
        DocumentFile f = getFile();
        return f != null && f.isFile();
    }

    @Override
    public long lastModified() {
        DocumentFile f = getFile();
        return f != null ? f.lastModified() : -1;
    }

    @Override
    public long length() {
        DocumentFile f = getFile();
        return f != null ? f.length() : -1;
    }

    @Override
    public boolean createNewFile() throws IOException {
        DocumentFile treeDocumentFile = DocumentUtil.getTreeDocumentFile(javaFile.getParent(), true);
        if (treeDocumentFile != null){
            final String name = this.javaFile.getName();
            try {
                final DocumentFile file = treeDocumentFile.createFile("*/*", name);
                return file != null && file.exists();
            }catch (Exception e){
                throw new IOException(e);
            }
        }else {
            throw new IOException("Path " + this.path + " is closed fail.");
        }
    }

    @Override
    public boolean delete() {
        DocumentFile f = getFile();
        return f != null && f.delete();
    }

    @Override
    public String[] list() {
        DocumentFile f = getFile();
        if (f != null){
            if (isDirectory()){
                final DocumentFile[] documentFiles = getFile().listFiles();
                final int length = documentFiles.length;
                final String[] fileList = new String[length];
                for (int i = 0; i < length; i++) {
                    fileList[i] = getPath() + "/" + documentFiles[i].getName();
                }
                return fileList;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    @Override
    public boolean mkdirs() {
        final String id = DocumentUtil.isData(this.path) ? "/data" : "/obb";
        if (DocumentUtil.atLeastTiramisu()){
            final String packageName = DocumentUtil.getPackageName(this.path);
            final String rootPath = DocumentUtil.DOCUMENT_ROOT + id +  "/" + packageName;
            final Uri rootUri = DocumentUtil.getRootUri(UriUtil.path2UriString(rootPath));
            if (rootUri != null){
                DocumentFile documentFile = DocumentFile.fromTreeUri(FileApplication.getApplication(), rootUri);
                if (documentFile != null){
                    final String pathContent = DocumentUtil.getPathContent(this.path, id +  "/" + packageName);
                    final String[] contentItem = pathContent.split("/");
                    for (String item : contentItem) {
                        final DocumentFile findFile = documentFile.findFile(item);
                        if (findFile == null) {
                            final DocumentFile createFile = documentFile.createDirectory(item);
                            if (createFile == null) {
                                return false;
                            }
                            documentFile = createFile;
                        }
                    }
                    return documentFile.exists();
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }else {
            final String rootPath = DocumentUtil.DOCUMENT_ROOT + id;
            final Uri rootUri = DocumentUtil.getRootUri(UriUtil.path2UriString(rootPath));
            if (rootUri != null){
                DocumentFile documentFile = DocumentFile.fromTreeUri(FileApplication.getApplication(), rootUri);
                if (documentFile != null){
                    final String pathContent = DocumentUtil.getPathContent(this.path, id);
                    final String[] contentItem = pathContent.split("/");
                    for (String item : contentItem) {
                        final DocumentFile findFile = documentFile.findFile(item);
                        if (findFile == null) {
                            final DocumentFile createFile = documentFile.createDirectory(item);
                            if (createFile == null) {
                                return false;
                            }
                            documentFile = createFile;
                        }
                    }
                    return documentFile.exists();
                }else {
                    return false;
                }
            }else {
                return false;
            }
        }
    }

    @Override
    public boolean renameTo(String dest) {
        DocumentFile f = getFile();
        return f != null && f.renameTo(dest);
    }
}
