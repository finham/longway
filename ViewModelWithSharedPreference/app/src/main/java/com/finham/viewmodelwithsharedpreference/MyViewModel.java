package com.finham.viewmodelwithsharedpreference;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;

/**
 * User: Fin
 * Date: 2020/1/29
 * Time: 22:34
 */
public class MyViewModel extends AndroidViewModel { //继承自AndroidViewModel
    SavedStateHandle handle;
    //使用static修饰就无法调用getApplication
    String key = getApplication().getResources().getString(R.string.key);
    //Non-static method getApplication() can't not be referenced from a static context
    String spName = getApplication().getResources().getString(R.string.sp);

    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        this.handle = handle;
        //如果handle里面没有我要保存的数据，那么就需要从SP里面读取；如果有那就从handle里读取
        if (!handle.contains(key)) { //如果SavedState内没有包含key这个键对应的值
            load();
        }
    }

    public LiveData<Integer> getNumber() { //public完全可见 private仅当前类可见 protected自己和子类可见 default同包内可见
        return handle.getLiveData(key);
    }

    private void load() { //加载方法
        SharedPreferences sp = getApplication().getSharedPreferences(spName, Context.MODE_PRIVATE);
        int x = sp.getInt(key, 0);//从sp中获取key键对应的值
        handle.set(key, x);//把键值对传入SavedStateHandle中
    }

    void save() {
        SharedPreferences sp = getApplication().getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, getNumber().getValue());//getNumber返回的是LiveData
        editor.apply(); //apply是异步的，延迟100ms；commit是同步的。
    }

    public void add(int x) {
        handle.set(key, getNumber().getValue() + x);
        //save(); //save放在这里是最可靠的，但是性能上是稍差一点的，因为操作频繁；第二可靠是去onPause里save
    }
}
