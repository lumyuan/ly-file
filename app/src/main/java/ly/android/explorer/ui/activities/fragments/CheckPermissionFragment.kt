package ly.android.explorer.ui.activities.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ly.android.explorer.R
import ly.android.explorer.common.bind
import ly.android.explorer.databinding.FragmentCheckPermissionBinding
import ly.android.explorer.ui.base.BaseFragment
import ly.android.explorer.util.ToastUtils
import ly.android.io.common.Permissions
import ly.android.io.util.DocumentUtil

class CheckPermissionFragment : BaseFragment(), OnClickListener {

    private val binding by bind(FragmentCheckPermissionBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun initView(root: View) {
        super.initView(root)
        if (DocumentUtil.atLeastR()){
            binding.esLayout.visibility = View.GONE
            binding.msLayout.visibility = View.VISIBLE
        }else {
            binding.esLayout.visibility = View.VISIBLE
            binding.msLayout.visibility = View.GONE
        }

        binding.esLayout.setOnClickListener(this)
        binding.msLayout.setOnClickListener(this)
        binding.start.setOnClickListener(this)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CheckPermissionFragment()
    }

    override fun onClick(v: View) {
        when (v) {
            binding.esLayout -> {
                if (!Permissions.hasCallExternalStorage()){
                    activity?.apply {
                        Permissions.getCallExternalStorage(this)
                    }
                }
            }
            binding.msLayout -> {
                if (!Permissions.hasCallAllFile()){
                    Permissions.getCallAllFile()
                }
            }
            binding.start -> {
                if (allowPermissions()){
                    replaceFragment(FileViewFragment.newInstance())
                }
            }
        }
    }

    override fun loadData() {
        super.loadData()
        binding.esCheckBox.isChecked = Permissions.hasCallExternalStorage()
        binding.msCheckBox.isChecked = Permissions.hasCallAllFile()
    }

    private fun allowPermissions(): Boolean {
        return if (DocumentUtil.atLeastR()){
            val hasCallAllFile = Permissions.hasCallAllFile()
            if (!hasCallAllFile){
                ToastUtils.toast(R.string.please_get_ms)
            }
            hasCallAllFile
        }else {
            val hasCallExternalStorage = Permissions.hasCallExternalStorage()
            if (!hasCallExternalStorage){
                ToastUtils.toast(R.string.please_get_es)
            }
            hasCallExternalStorage
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        activity?.apply {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.mainActivityFrameLayout, fragment)
                setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit)
            }.commit()
        }
    }
}