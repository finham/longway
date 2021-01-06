package com.finham.calculationtest;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.finham.calculationtest.databinding.FragmentQuestionBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {
    private StringBuilder builder = new StringBuilder();

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        final ScoreViewModel scoreViewModel = new ViewModelProvider(requireActivity(), //ViewModel是跟Activity挂钩、关联的，所以Fragment请求的都是同一个
                new SavedStateViewModelFactory(requireActivity().getApplication(), requireActivity())).get(ScoreViewModel.class);
        scoreViewModel.generator();//由于每次进入要新创建，所以之前左右数字和操作符号的就全没了
        scoreViewModel.getCurrentScore().setValue(0); //然后又把CurrentScore设置为0
        //而以前的项目没有这些限制，而是
        final FragmentQuestionBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false);
        binding.setData(scoreViewModel);
        binding.setLifecycleOwner(requireActivity());
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button0:
                        builder.append("0");
                        break;
                    case R.id.button1:
                        builder.append("1");
                        break;
                    case R.id.button2:
                        builder.append("2");
                        break;
                    case R.id.button3:
                        builder.append("3");
                        break;
                    case R.id.button4:
                        builder.append("4");
                        break;
                    case R.id.button5:
                        builder.append("5");
                        break;
                    case R.id.button6:
                        builder.append("6");
                        break;
                    case R.id.button7:
                        builder.append("7");
                        break;
                    case R.id.button8:
                        builder.append("8");
                        break;
                    case R.id.button9:
                        builder.append("9");
                        break;
                    case R.id.button_clear:
                        builder.setLength(0);
                        break;
                }
                if (builder.length() == 0) {
                    binding.textView9.setText(getString(R.string.input_indicator));//如果不用getString，那么会返回一串int整数，setText参数也是int
                } else {
                    binding.textView9.setText(builder.toString());
                }
            }
        };

        binding.button0.setOnClickListener(listener);
        binding.button1.setOnClickListener(listener);
        binding.button2.setOnClickListener(listener);
        binding.button3.setOnClickListener(listener);
        binding.button4.setOnClickListener(listener);
        binding.button5.setOnClickListener(listener);
        binding.button6.setOnClickListener(listener);
        binding.button7.setOnClickListener(listener);
        binding.button8.setOnClickListener(listener);
        binding.button9.setOnClickListener(listener);
        binding.buttonClear.setOnClickListener(listener);
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View view) { //将Integer转为int
                if (builder.length() == 0) {
                    builder.append("-1");
                }
                if (Integer.valueOf(builder.toString()).intValue() == (scoreViewModel.getAnswer().getValue())) {
                    scoreViewModel.answerCorrectly();
                    builder.setLength(0);
                    binding.textView9.setText(R.string.correct_answer_message);
                } else {
                    NavController controller = Navigation.findNavController(view);
                    if (scoreViewModel.win_flag) {
                        //Navigation.createNavigateOnClickListener(R.id.action_questionFragment_to_winFragment); 用这种跳转不过去
                        controller.navigate(R.id.action_questionFragment_to_winFragment);
                        scoreViewModel.win_flag = false;
                        scoreViewModel.save();
                    } else {
                        //Navigation.createNavigateOnClickListener(R.id.action_questionFragment_to_loseFragment);
                        controller.navigate(R.id.action_questionFragment_to_loseFragment);
                    }
                }
            }
        });
        return binding.getRoot();
    }

}
