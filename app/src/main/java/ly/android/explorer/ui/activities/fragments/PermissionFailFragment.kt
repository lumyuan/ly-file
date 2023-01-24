package ly.android.explorer.ui.activities.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import ly.android.explorer.R
import ly.android.explorer.common.bind
import ly.android.explorer.databinding.FragmentPermissionFailBinding
import ly.android.explorer.ui.base.BaseFragment
import ly.android.io.common.Permissions

class PermissionFailFragment : BaseFragment(), OnClickListener {

    private val binding by bind(FragmentPermissionFailBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun initView(root: View) {
        super.initView(root)
        binding.checkPermission.setOnClickListener(this)
    }

    private fun replaceFragment(fragment: Fragment) {
        activity?.apply {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.mainActivityFrameLayout, fragment)
                setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit)
            }.commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PermissionFailFragment()
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.checkPermission -> {
                activity?.apply {
                    replaceFragment(CheckPermissionFragment.newInstance())
                    Permissions.getCallExternalStorage(this)
                }
            }
        }
    }
}