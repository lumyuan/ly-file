package ly.android.io;

import org.junit.Test;

import static org.junit.Assert.*;

import android.os.Environment;

import androidx.annotation.IdRes;

import ly.android.io.util.DocumentUtil;
import ly.android.io.util.FileSort;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void documentTest(){
        DocumentUtil.getTreeDocumentFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/aaa", false);
    }
}