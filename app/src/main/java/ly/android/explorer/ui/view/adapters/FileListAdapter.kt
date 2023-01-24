package ly.android.explorer.ui.view.adapters

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ly.android.explorer.Application
import ly.android.explorer.R
import ly.android.explorer.databinding.ItemFileBinding
import ly.android.explorer.ui.MainActivityViewModel
import ly.android.io.File
import ly.android.io.common.IOUtils
import ly.android.io.media.MediaType
import ly.android.io.util.DocumentUtil
import ly.android.io.util.FileUtil
import ly.android.io.util.UriUtil
import java.text.SimpleDateFormat

class FileListAdapter(private val mainActivityViewModel: MainActivityViewModel): Adapter<FileListAdapter.MyHolder>(){

    private val list = ArrayList<File>()

    class MyHolder(val binding: ItemFileBinding): ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(
            ItemFileBinding.bind(
                View.inflate(parent.context, R.layout.item_file, null)
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SimpleDateFormat")
    private val dateFormat = SimpleDateFormat(Application.application.getString(R.string.date_format))

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val binding = holder.binding
        val file = list[position]
        val date = dateFormat.format(file.lastModified())

        binding.headLine.text = file.name
        binding.root.tag = file.path

        if (file.isDirectory){
            loadImage(binding.icon, R.drawable.file_directory_icon)
            binding.gotoFolder.visibility = View.VISIBLE
            Observable.create<Int> {
                val childList = file.list()
                it.onNext(
                    childList?.size ?: 0
                )
                it.onComplete()
            }.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    if (binding.root.tag != file.path){
                        return@subscribe
                    }
                    binding.supportText.text = "$date\t\t|\t\t$it${Application.application.getString(R.string.count)}"
                }
        }else {
            loadImage(binding.icon, file.path)
            binding.gotoFolder.visibility = View.GONE
            binding.supportText.text = "$date\t\t|\t\t${FileUtil.readableFileSize(file.length())}"
        }

        binding.root.setOnClickListener {

        }
    }

    private fun loadImage(imageView: ImageView, @RawRes @DrawableRes id: Int){
        Glide.with(imageView).load(id).into(imageView)
    }

    @SuppressLint("CheckResult")
    private fun loadImage(imageView: ImageView, path: String){
        when (MediaType.getFileType(path)) {
            MediaType.Type.UNKNOWN -> {
                loadImage(imageView, R.drawable.file_generic_icon)
            }
            MediaType.Type.TEXT -> {
                loadImage(imageView, R.drawable.file_text_icon)
            }
            MediaType.Type.IMAGE -> {
                Glide.with(imageView)
                    .load(path)
                    .placeholder(R.drawable.file_image_icon)
                    .error(R.drawable.file_image_icon)
                    .into(imageView)
            }
            MediaType.Type.VIDEO -> {
                Glide.with(imageView)
                    .load(path)
                    .placeholder(R.drawable.file_video_icon)
                    .error(R.drawable.file_video_icon)
                    .into(imageView)
            }
            MediaType.Type.ZIP -> {
                loadImage(imageView, R.drawable.file_archive_icon)
            }
            MediaType.Type.DOC -> {

            }
            MediaType.Type.CODE -> {
                loadImage(imageView, R.drawable.file_code_icon)
            }
            MediaType.Type.APP -> {
                loadImage(imageView, R.drawable.file_apk_icon)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(files: Array<File>){
        this.list.clear()
        notifyDataSetChanged()
        for (index in files.indices) {
            this.list.add(files[index])
            notifyItemChanged(index - 1)
            notifyItemInserted(index)
        }
    }
}