package com.finham.calculationtest;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finham.calculationtest.databinding.FragmentWinBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class WinFragment extends Fragment {


    public WinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ScoreViewModel scoreViewModel=new ViewModelProvider(requireActivity(),new SavedStateViewModelFactory(requireActivity().getApplication(),requireActivity())).get(ScoreViewModel.class);
        FragmentWinBinding binding= DataBindingUtil.inflate(inflater,R.layout.fragment_win,container,false);
        binding.setData(scoreViewModel);
        binding.setLifecycleOwner(requireActivity());
        binding.buttonWinBack.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_winFragment_to_titleFragment));
        return binding.getRoot();
    }

}
