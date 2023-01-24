package ly.android.explorer.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ly.android.explorer.entity.PathTab

class MainActivityViewModel: ViewModel() {

    companion object {

        fun createList(path: String): ArrayList<PathTab> {
            val list = ArrayList<PathTab>()
            val stringList = getPath(path).split("/")
            for (index in stringList.indices) {
                list.add(
                    PathTab(
                        stringList[index], index.toLong()
                    )
                )
            }
            return list
        }

        private fun getPath(path: String): String {
            return if (path.startsWith("/")){
                path.substring(1)
            }else {
                path
            }
        }
    }

    val path = MutableLiveData<String>()



}