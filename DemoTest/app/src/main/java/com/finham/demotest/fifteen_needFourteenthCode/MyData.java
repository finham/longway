package com.finham.demotest.fifteen_needFourteenthCode;

import android.content.Context;
import android.content.SharedPreferences;

import com.finham.demotest.R;

/**
 * 第十五课
 * User: Fin
 * Date: 2020/1/26
 * Time: 17:23
 */
public class MyData {
    public int number;
    private Context context;

    public MyData(Context context) {
        this.context = context;
    }

    public void save(){
        String name=context.getResources().getString(R.string.MY_DATA);
        SharedPreferences sp=context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt(context.getResources().getString(R.string.MY_KEY),1);
        editor.apply(); //you won't be notified of any failures
    }

    public int load(){
        String name=context.getResources().getString(R.string.MY_DATA);
        SharedPreferences sp=context.getSharedPreferences(name,Context.MODE_PRIVATE);
        int x=sp.getInt(context.getResources().getString(R.string.MY_KEY),R.integer.defaultValue);
        number=x;
        return x;
    }
}
