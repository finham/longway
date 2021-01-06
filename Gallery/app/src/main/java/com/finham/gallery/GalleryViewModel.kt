package com.finham.gallery

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson

/**
 * User: Fin
 * Date: 2020/3/1
 * Time: 13:45
 */
class GalleryViewModel(application: Application) : AndroidViewModel(application) {
    private val _photoListLive = MutableLiveData<List<PhotoItem>>()
    val photoListView: LiveData<List<PhotoItem>>
        get() {
            return _photoListLive
        }
    // 或者这样写：get() = _photoListLive

    //需要从网上加载数据，更好的做法应该是写一个类来加载数据，然后在ViewModel里调用那个类，这样层次会更好~
    //这里为了简单处理就不另外写一个类了，直接写个方法
    fun fetchData() {
        val stringRequest = StringRequest(Request.Method.GET, getURL(),
            Response.Listener {
                _photoListLive.value = Gson().fromJson(it, Pixabay::class.java).hits.toList()
            },
            Response.ErrorListener {
                Log.d("Hello", "Error happened:$it")
                Toast.makeText(getApplication(), "Error happened:$it", Toast.LENGTH_SHORT).show()
            }
        )
        VolleySingleton.getInstance(getApplication()).requestQueue.add(stringRequest)
    }

    private fun getURL(): String {
        //文档中默认default是返回20个，可接受的是3-200。文档地址：https://pixabay.com/api/docs/#api_rate_limit
        return "https://pixabay.com/api/?key=12472743-874dc01dadd26dc44e0801d61&q=${keywords.random()}&per_page=100"
    }

    private val keywords =
        arrayOf("cat", "dog", "car", "beauty", "flower")  //做一个随机化处理，就是每次下拉刷新在五个关键词中选一个挑选生成新的请求
}