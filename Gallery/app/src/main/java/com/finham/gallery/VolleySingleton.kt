package com.finham.gallery

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

/**
 * User: Fin
 * Date: 2020/3/1
 * Time: 13:58
 */
//Volley的单例类，只允许在该进程中只有一个实例
class VolleySingleton private constructor(context: Context) { //我发现了kt的构造方法都是直接声明在类名后面的
    //companion object对应于Java中的static
    companion object {
        private var instance: VolleySingleton? = null
        fun getInstance(context: Context) = //{ 这里的括号要删，不然下面的requestQueue在GalleryViewModel中无法引用？？？我也不知道为啥。。。
            instance ?: synchronized(this) {
                VolleySingleton(context).also { instance = it }
            }
        //}
    }

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext) //保证全局唯一
    }
}