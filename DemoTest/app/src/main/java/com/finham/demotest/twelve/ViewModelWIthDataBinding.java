package com.finham.demotest.twelve;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Android Studio.
 * User: Fin
 * Date: 2020/1/24
 * Time: 11:27
 */
public class ViewModelWIthDataBinding extends ViewModel {
    private MutableLiveData<Integer> number;

    public MutableLiveData<Integer> getNumber() {
        if(number==null){
            number=new MutableLiveData<>();
            number.setValue(0);
        }
        return number;
    }

    public void add(){
        number.setValue(number.getValue()+1); //LiveData之所以能够通知界面刷新数据，就是通过setValue()
    }

}
