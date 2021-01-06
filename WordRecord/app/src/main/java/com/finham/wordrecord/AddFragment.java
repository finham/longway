package com.finham.wordrecord;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {
    private Button button;
    private EditText english, chinese;
    private WordViewModel wordViewModel;
    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = requireActivity();
        wordViewModel=new ViewModelProvider(requireActivity()).get(WordViewModel.class);
        english = activity.findViewById(R.id.editTextEnglish);
        chinese = requireActivity().findViewById(R.id.editTextChinese);
        button = requireActivity().findViewById(R.id.buttonAdd);
        button.setEnabled(false);//一开始启动页面，输入任何东西之前按钮是不可用的
        //一启动页面就默认聚焦在EditText且弹出键盘
        english.requestFocus();//获取焦点
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(english, 0);
        }
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String englishText = english.getText().toString().trim();
                String chineseText = chinese.getText().toString().trim();
                button.setEnabled(!englishText.isEmpty() && !chineseText.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        english.addTextChangedListener(textWatcher);
        chinese.addTextChangedListener(textWatcher);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WordEntity wordEntity=new WordEntity(english.getText().toString().trim(),chinese.getText().toString().trim());
                wordViewModel.insertWords(wordEntity);
                NavController navController= Navigation.findNavController(view);
                navController.navigateUp();
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
    }
}
