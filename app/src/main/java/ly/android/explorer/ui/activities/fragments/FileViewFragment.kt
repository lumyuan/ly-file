package ly.android.explorer.ui.activities.fragments

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ly.android.explorer.common.bind
import ly.android.explorer.databinding.FragmentFileViewBinding
import ly.android.explorer.ui.MainActivityViewModel
import ly.android.explorer.ui.base.BaseFragment
import ly.android.explorer.ui.view.adapters.FileListAdapter
import ly.android.explorer.ui.view.adapters.PathTabAdapter
import ly.android.io.File
import ly.android.io.annotate.Order
import ly.android.io.annotate.Order.ASCENDING
import ly.android.io.common.Permissions
import ly.android.io.util.DocumentUtil
import ly.android.io.util.FileSort

class FileViewFragment : BaseFragment() {

    private val binding by bind(FragmentFileViewBinding::inflate)
    private val mainActivityViewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FileViewFragment()
    }

    private lateinit var tabAdapter: PathTabAdapter
    private lateinit var fileListAdapter: FileListAdapter
    override fun initView(root: View) {
        super.initView(root)
        tabAdapter = PathTabAdapter(mainActivityViewModel)
        fileListAdapter = FileListAdapter(mainActivityViewModel)

        binding.pathTabView.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
            adapter = tabAdapter
        }

        binding.fileListView.apply {
            layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            adapter = fileListAdapter
        }

    }

    override fun loadSingleData() {
        super.loadSingleData()
        val stringBuilder = StringBuilder()
        Thread {
            for (i in 0 until 10){
                try {
                    Thread.sleep(1000)
                }catch (e: Exception){
                    e.printStackTrace()
                }
                val toString = stringBuilder.append("/").append('A' + i).toString()
                val createList = MainActivityViewModel.createList(toString)
                activity?.runOnUiThread {
                    tabAdapter.updatePath(createList)
                }
            }
        }.start()
    }

    override fun loadData() {
        super.loadData()

    }
}