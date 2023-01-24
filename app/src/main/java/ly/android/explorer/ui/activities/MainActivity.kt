package ly.android.explorer.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ly.android.explorer.R
import ly.android.explorer.common.bind
import ly.android.explorer.databinding.ActivityMainBinding
import ly.android.explorer.ui.MainActivityViewModel
import ly.android.explorer.ui.activities.fragments.CheckPermissionFragment
import ly.android.explorer.ui.activities.fragments.FileViewFragment
import ly.android.io.common.Permissions
import ly.android.io.util.DocumentUtil

class MainActivity : AppCompatActivity() {

    private val binding by bind(ActivityMainBinding::inflate)
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        initFrame()
    }

    private fun initFrame() {
        if (allowPermissions()){
            replaceFragment(FileViewFragment.newInstance())
        }else {
            replaceFragment(CheckPermissionFragment.newInstance())
        }
    }

    private fun allowPermissions(): Boolean {
        return if (DocumentUtil.atLeastR()){
            Permissions.hasCallAllFile()
        }else {
            Permissions.hasCallExternalStorage()
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.mainActivityFrameLayout.id, fragment)
            setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit)
        }.commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Permissions.savePermissions(this, requestCode, resultCode, data)
    }
}