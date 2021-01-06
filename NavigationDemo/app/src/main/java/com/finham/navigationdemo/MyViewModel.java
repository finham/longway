package com.finham.navigationdemo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * User: Fin
 * Date: 2020/2/1
 * Time: 19:14
 */
public class MyViewModel extends ViewModel {
    private MutableLiveData<Integer> number;

    public MutableLiveData<Integer> getNumber() {
        if (number == null) {
            number = new MutableLiveData<>();
            number.setValue(0);
        }
        return number;
    }

    public void add(int x) {
        number.setValue(getNumber().getValue()+x);
        if(number.getValue()<0){
            number.setValue(0);
        }
    }
}
