package com.finham.demotest.fourteen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.finham.demotest.fifteen_needFourteenthCode.MyData;
import com.finham.demotest.R;
import com.finham.demotest.databinding.ActivityRestoreBinding;

/**
 * 本Activity用来验证ViewModel的状态保存功能
 */
public class RestoreActivity extends AppCompatActivity {
    RestoreViewModel restoreViewModel;
    ActivityRestoreBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_restore);
        restoreViewModel= new ViewModelProvider(this,new SavedStateViewModelFactory(getApplication(),this)).get(RestoreViewModel.class);
//        if(savedInstanceState!=null){
//            restoreViewModel.getNumber().setValue(savedInstanceState.getInt("key"));
//        }

        binding.setData(restoreViewModel);
        binding.setLifecycleOwner(this);

        MyData myData=new MyData(getApplicationContext());
        myData.number=0;
        myData.save();
        int y=myData.load();
    }

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("key", restoreViewModel.getNumber().getValue());//getNumber返回的是LiveData
//    }
}
