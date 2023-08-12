package ly.android.explorer.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import ly.android.explorer.common.bind
import ly.android.explorer.data.OptimizePath
import ly.android.explorer.databinding.LayoutTestBinding
import ly.android.io.File
import ly.android.io.common.Permissions
import ly.android.io.util.DocumentUtil

class TestActivity : AppCompatActivity() {

    private val binding by bind(LayoutTestBinding::inflate)
    private val path =
        Environment.getExternalStorageDirectory().absolutePath + "/Android/data/test/a/b/c/d/e.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.getPermission.setOnClickListener {
            Permissions.getPreDir(this, DocumentUtil.REQ_SAF_R_DATA, path)
        }

        binding.mkdirs.setOnClickListener {
            val file = File(path)
            
            val parent = File(file.parent)
            if (!parent.exists()) {
                println(parent.mkdirs())
            }
            if (!file.exists()) {
                file.createNewFile()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Permissions.savePermissions(this, requestCode, resultCode, data)
    }
}