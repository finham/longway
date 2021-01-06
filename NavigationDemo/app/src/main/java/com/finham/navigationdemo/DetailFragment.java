package com.finham.navigationdemo;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String s = getArguments().getString("name");//通过这种方式取出参数
        String stringFromEditText = getArguments().getString("my_name");//动态获取
        Button button = getView().findViewById(R.id.button_to_home);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController controller = Navigation.findNavController(view); //找到Button所归属的NavController
                controller.navigate(R.id.action_detailFragment_to_homeFragment);
                //controller.navigate(R.id.detailFragment); //也可以这样，你这样相当于传了一个destination，而不是action，但不推荐
            }
        });
        TextView textView = getView().findViewById(R.id.textView2);
        textView.setText(stringFromEditText);
    }
}
