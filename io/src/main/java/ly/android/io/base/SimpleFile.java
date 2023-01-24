package ly.android.io.base;

import java.io.File;
import java.io.IOException;

public final class SimpleFile implements FileApi {

    private final File file;
    public SimpleFile(String path){
        file = new File(path);
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getParent() {
        return file.getParent();
    }

    @Override
    public String getPath() {
        return file.getPath();
    }

    @Override
    public boolean canRead() {
        return file.canRead();
    }

    @Override
    public boolean canWrite() {
        return file.canWrite();
    }

    @Override
    public boolean isDirectory() {
        return file.isDirectory();
    }

    @Override
    public boolean isFile() {
        return file.isFile();
    }

    @Override
    public long lastModified() {
        return file.lastModified();
    }

    @Override
    public long length() {
        return file.length();
    }

    @Override
    public boolean createNewFile() throws IOException {
        return file.createNewFile();
    }

    @Override
    public boolean delete() {
        return file.delete();
    }

    @Override
    public String[] list() {
        final String[] list = file.list();
        if (list == null){
            return null;
        }
        final int length = list.length;
        final String[] newList = new String[length];
        for (int i = 0; i < length; i++) {
            newList[i] = this.file.getPath() + "/" + list[i];
        }
        return newList;
    }

    @Override
    public boolean mkdirs() {
        return file.mkdirs();
    }

    @Override
    public boolean renameTo(String dest) {
        return file.renameTo(new File(getParent(), dest));
    }
}
