package com.finham.gallery

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * User: Fin
 * Date: 2020/3/1
 * Time: 11:40
 */
//查看 https://pixabay.com/api/docs/ 对应的返回
//data class的特性：相当于把所有域都添加进去，并且内部自动生成setter/getter等。相比Java数据类简单很多
data class Pixabay (
    val totalHits:Int,
    val hits:Array<PhotoItem>, //警告：data class中的Array建议重写equals/hashCode。此处不写也没关系，因为后面没有用到
    val total:Int
)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pixabay

        if (totalHits != other.totalHits) return false
        if (!hits.contentEquals(other.hits)) return false
        if (total != other.total) return false

        return true
    }

    override fun hashCode(): Int {
        var result = totalHits
        result = 31 * result + hits.contentHashCode()
        result = 31 * result + total
        return result
    }
}

//hits中有很多字段，只取其中三个字段
@Parcelize data class PhotoItem (
    @SerializedName("webformatURL") //SerializedName代表序列化后的名称，在吐司单词我也有用
    //val webformatURL:String, 将webformatURL使用Gson中的注解，然后将webformatURL改为previewURL。（其实不这样改也行，改了当然更好一点）
    val previewURL:String,
    @SerializedName("id")
    val photoId:Int,
    @SerializedName("largeImageURL")
    val fullURL:String
):Parcelable