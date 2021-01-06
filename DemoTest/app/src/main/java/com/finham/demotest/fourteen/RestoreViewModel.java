package com.finham.demotest.fourteen;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

/**
 * Created by Android Studio.
 * User: Fin
 * Date: 2020/1/25
 * Time: 21:29
 */
public class RestoreViewModel extends ViewModel {
    //private MutableLiveData<Integer> number;
    private SavedStateHandle handle;
    public RestoreViewModel(SavedStateHandle handle){
        this.handle=handle;
    }

    public MutableLiveData<Integer> getNumber() {
        if(!handle.contains("key")){
            handle.set("key",0);//相当于初始化，发生的时候就是程序第一次被加载进内存之时。
        }
        return handle.getLiveData("key");//以LiveData形式获取
        //if(number==null){
        //    number=new MutableLiveData<>();
        //    number.setValue(0);
        //}
        //return number;
    }

    public void add(){
        getNumber().setValue(getNumber().getValue()+1);
    }
}
