package com.finham.demotest.eleven;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Android Studio.
 * User: Fin
 * Date: 2020/1/23
 * Time: 22:38
 */
public class ViewModelWithLiveData extends ViewModel {
    private MutableLiveData<Integer> likedNumber; //MutableLiveData是一个容器,当然是设成private

//    public ViewModelWithLiveData() {
//        likedNumber=new MutableLiveData<>();
//        likedNumber.setValue(0);
//    }

    public MutableLiveData<Integer> getLikedNumber() {
        //比起构造函数里初始化，笔者更推荐此种写法。
        if(likedNumber == null){
            likedNumber=new MutableLiveData<>();
            likedNumber.setValue(0);
        }
        return likedNumber;
    }

//    public void setLikedNumber(MutableLiveData<Integer> likedNumber){ //不允许用户直接操作
//        this.likedNumber = likedNumber;
//    }

    //不允许直接设置多少，而是采用一个个叠加的方式
    public void addLikedNumber(int n){
        likedNumber.setValue(likedNumber.getValue()+n);
    }

}
