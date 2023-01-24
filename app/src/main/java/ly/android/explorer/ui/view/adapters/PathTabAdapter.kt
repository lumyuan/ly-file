package ly.android.explorer.ui.view.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ly.android.explorer.R
import ly.android.explorer.databinding.ItemPathTabBinding
import ly.android.explorer.entity.PathTab
import ly.android.explorer.ui.MainActivityViewModel

class PathTabAdapter(private val mainActivityViewModel: MainActivityViewModel): Adapter<PathTabAdapter.MyHolder>() {

    class MyHolder(val binding: ItemPathTabBinding)
        : ViewHolder(binding.root)

    private val list: ArrayList<PathTab> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            ItemPathTabBinding.bind(
                View.inflate(
                    parent.context, R.layout.item_path_tab, null
                )
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val binding = holder.binding
        val pathTab = list[position]
        binding.tabChip.text = pathTab.name
        binding.lastTabChip.text = pathTab.name
        binding.left.visibility = if (position == 0){
            View.VISIBLE
        }else {
            View.GONE
        }
        binding.rightIcon.visibility = if (position == itemCount - 1){
            View.GONE
        }else {
            View.VISIBLE
        }
        if (position == itemCount - 1){
            binding.tabChip.visibility = View.GONE
            binding.lastTabChip.visibility = View.VISIBLE
        }else {
            binding.tabChip.visibility = View.VISIBLE
            binding.lastTabChip.visibility = View.GONE
        }
        binding.tabChip.setOnClickListener {
            val realPath = getRealPath(position)
            val pathTabs = MainActivityViewModel.createList(realPath)
            updatePath(pathTabs)
        }
    }

    private fun getRealPath(position: Int): String{
        val stringBuilder = StringBuilder()
        for (i in 0 .. position){
            stringBuilder.append("/").append(list[i].name)
        }
        return stringBuilder.toString()
    }

    fun updatePath(list: ArrayList<PathTab>){
        if (this.list.size < list.size){//add
            for(i in this.list.size until list.size){
                this.list.add(list[i])
                notifyItemInserted(itemCount)
                notifyItemChanged(itemCount - 2)
            }
        }else if (this.list.size > list.size){//remove
            for (i in this.list.size - 1 downTo list.size){
                this.list.removeAt(i)
                notifyItemRemoved(i)
                notifyItemChanged(itemCount - 1)
            }
        }else {//update
            this.list.clear()
            for (index in list.indices) {
                this.list.add(list[index])
                notifyItemInserted(index)
            }
        }
    }
}