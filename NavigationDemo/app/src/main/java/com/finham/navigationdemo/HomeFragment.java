package com.finham.navigationdemo;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.finham.navigationdemo.databinding.FragmentHomeBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final MyViewModel myViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        FragmentHomeBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(getActivity());
        binding.buttonToAddSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_homeFragment_to_addSubtractFragment);
            }
        });
        binding.seekBar.setProgress(myViewModel.getNumber().getValue());
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                myViewModel.getNumber().setValue(i); //
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getView().findViewById(R.id.button).setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_detailFragment)); //更简单的写法

        getView().findViewById(R.id.button_to_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //动态传递参数的写法，例如你要把EditText里的文字传递过去
                EditText editText = getView().findViewById(R.id.editText);
                String s = editText.getText().toString();
                if (TextUtils.isEmpty(s)) {
                    Toast.makeText(getActivity(), "请输入名字", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("my_name", s);
                //这样子写跳转不过去，估计和setOnClickListener有固定格式搭配，得改成以下两句
                //Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_detailFragment,bundle);
                NavController controller = Navigation.findNavController(view); //找到Button所归属的NavController
                controller.navigate(R.id.action_homeFragment_to_detailFragment, bundle);
            }
        });
    }
}
