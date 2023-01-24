package ly.android.io.util;

import java.util.Arrays;

import ly.android.io.File;
import ly.android.io.annotate.OrderBy;

public class FileSort {

    /**
     * 按名称排序
     * @param files 文件列表
     * @param orderBy 排序规则
     */
    public static void byName(File[] files, @OrderBy int orderBy){
        if (files == null)
            return;
        Arrays.sort(files, (o1, o2) -> {
            if (o1.isDirectory() && o2.isFile())
                return -1;
            if (o1.isFile() && o2.isDirectory())
                return 1;
            return o1.getName().compareTo(o2.getName()) * orderBy;
        });
    }

    /**
     * 按更新时间排序
     * @param files 文件列表
     * @param orderBy 排序规则
     */
    public static void byDate(File[] files, @OrderBy int orderBy){
        if (files == null)
            return;
        Arrays.sort(files, (o1, o2) -> {
            if (o1.isDirectory() && o2.isFile())
                return -1;
            if (o1.isFile() && o2.isDirectory())
                return 1;
            long diff = o1.lastModified() - o2.lastModified();
            if (diff > 0)
                return orderBy;
            else if (diff == 0)
                return 0;
            else
                return -1 * orderBy;
        });
    }

    /**
     * 按文件大小排序
     * @param files 文件列表
     * @param orderBy 排序规则
     */
    public static void byLength(File[] files, @OrderBy int orderBy){
        if (files == null)
            return;
        Arrays.sort(files, (o1, o2) -> {
            if (o1.isDirectory() && o2.isFile())
                return -1;
            if (o1.isFile() && o2.isDirectory())
                return 1;
            long diff = o1.length() - o2.length();
            if (diff > 0)
                return orderBy;
            else if (diff == 0)
                return 0;
            else
                return -1 * orderBy;
        });
    }
}
