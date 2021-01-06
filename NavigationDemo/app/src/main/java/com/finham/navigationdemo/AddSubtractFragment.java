package com.finham.navigationdemo;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finham.navigationdemo.databinding.FragmentAddSubtractBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddSubtractFragment extends Fragment {

    public AddSubtractFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MyViewModel myViewModel = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        FragmentAddSubtractBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_subtract, container, false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(getActivity());
        binding.buttonReturn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_addSubtractFragment_to_homeFragment));
        return binding.getRoot();
    }

}
