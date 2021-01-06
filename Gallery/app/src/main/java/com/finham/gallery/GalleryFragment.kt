package com.finham.gallery

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.GridLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_gallery.*

/**
 * A simple [Fragment] subclass.
 */
class GalleryFragment : Fragment() {
    private lateinit var galleryViewModel: GalleryViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val galleryAdapter = GalleryAdapter()
        recyclerView.apply {
            adapter = galleryAdapter
            layoutManager = GridLayoutManager(this.context, 2)
        }
        galleryViewModel = ViewModelProvider(
            this//,ViewModelProvider.AndroidViewModelFactory(requireActivity().application) //加不加我好像都没报错
        ).get(GalleryViewModel::class.java)
        // 如果要只对应Fragment的生命周期的话，第一个参数要设为getViewLifecycleOwner()；如果是Activity则getActivity()
        galleryViewModel.photoListView.observe(viewLifecycleOwner, Observer {
            galleryAdapter.submitList(it)
            swipeRefreshLayout.isRefreshing = false
        })
        // 如果为空就执行fetchData，因为首次加载进来里面什么内容都没有就不需要手动取数据，而是直接加载内容。。（不知道在讲啥）
        // 反正就是首次进来就得去取数据就是了
        galleryViewModel.photoListView.value ?: galleryViewModel.fetchData()

        //官方对于SwipeRefershLayout的使用还建议在Menu中添加一个，因为不是所有人都知道可以这样刷新的。
        swipeRefreshLayout.setOnRefreshListener { galleryViewModel.fetchData() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                swipeRefreshLayout.isRefreshing = true
                //因为如果加载很快那么刷新就一闪而过很奇怪，所以给它做一个延时1秒
                Handler().postDelayed(Runnable { galleryViewModel.fetchData() }, 500)
            }
            //R.id.fragment -> galleryViewModel.fetchData()
            //else -> galleryViewModel.fetchData()
        }
        return super.onOptionsItemSelected(item)
    }
}
