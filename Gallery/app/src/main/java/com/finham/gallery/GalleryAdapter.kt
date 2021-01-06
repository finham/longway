package com.finham.gallery

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.cell_gallery.view.*

/**
 * User: Fin
 * Date: 2020/3/1
 * Time: 15:49
 */
//不要导入android.widget.ListAdapter，要的是androidx.recyclerview.widget.ListAdapter
class GalleryAdapter :
    ListAdapter<PhotoItem, MyViewHolder>(DIFF_CALLBACK) { //第一个参数是Type，即要放入什么东西类型；

    // object相当于静态对象
    object DIFF_CALLBACK : DiffUtil.ItemCallback<PhotoItem>() {
        override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean { //判断item是否相同
            return oldItem === newItem //kt中==比较的是数值是否相等, 而===比较的是两个对象的地址是否相等
            // https://blog.csdn.net/c1392851600/article/details/80571969
        }

        override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
            return oldItem.photoId == newItem.photoId
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder { //判断item的内容是否相同
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_gallery, parent, false);
        val holder = MyViewHolder(view)
        holder.itemView.setOnClickListener {
            Bundle().apply {
                putParcelable("photo", getItem(holder.adapterPosition))
                holder.itemView.findNavController()
                    .navigate(R.id.action_galleryFragment_to_photoFragment, this)
            }
        } //不要把Listener写在onBindViewHolder
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // shimmerLayout需要做挺多事情，所以使用apply
        holder.itemView.shimmerLayout.apply {
            setShimmerColor(0x55FFFFFF)
            setShimmerAngle(0)
            startShimmerAnimation()
        }

        Glide.with(holder.itemView)
            .load(getItem(position).previewURL)
            .placeholder(R.drawable.ic_photo_gray_24dp)
            .listener(object : RequestListener<Drawable> {
                // 注意这边两个方法都是要return false，如果return true则无法显示图片
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    // 注意这边要判空，否则程序可能会崩溃。如果你在图片加载完成之前就切换走了，那么Listener还是会呼叫这里，Shimmer还没初始化完
                    // 注意Shimmer本质是一个Layout，用来给图片加上闪光效果
                    return false.also { holder.itemView.shimmerLayout?.stopShimmerAnimation() }
                }
            }) //此处的RequestListener是一个接口，此处对应Java中的匿名内部类
            .into(holder.itemView.imageView)
    }
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)