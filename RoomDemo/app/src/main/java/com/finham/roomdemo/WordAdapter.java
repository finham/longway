package com.finham.roomdemo;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Fin
 * Date: 2020/2/9
 * Time: 22:46
 */
public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {
    private List<WordEntity> words = new ArrayList<>();
    private boolean isCard;
    private WordViewModel wordViewModel;

    WordAdapter(boolean isCard,WordViewModel wordViewModel) {
        this.isCard = isCard;
        this.wordViewModel=wordViewModel;
    }

    public WordAdapter(List<WordEntity> wordEntities) {
        this.words = wordEntities;
    }

    void setWords(List<WordEntity> words) {
        this.words = words;
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (isCard) {
            return new WordViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_card_2, parent, false));
        }
        return new WordViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_normal_2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final WordViewHolder holder, int position) {
        final WordEntity wordEntity = words.get(position);
        holder.number.setText(String.valueOf(position));
        holder.english.setText(wordEntity.getWord());
        holder.chinese.setText(wordEntity.getMeaning());
        //我们希望setChecked()是来初始化一个状态的，不希望初始化的时候会驱动下面的Listener，加上这一句：
        holder.aSwitch.setOnCheckedChangeListener(null); //先把Listener设置为空，没加这句会有bug
        if (wordEntity.isChineseInvisible()) { //如果为true，代表不要显示中文
            holder.chinese.setVisibility(View.GONE);
            holder.aSwitch.setChecked(true);
        } else {
            holder.chinese.setVisibility(View.VISIBLE);
            holder.aSwitch.setChecked(false);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://m.youdao.com/dict?le=eng&q=" + holder.english.getText());
                Intent intent = new Intent(Intent.ACTION_VIEW); //浏览网站的action
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    holder.chinese.setVisibility(View.GONE);
                    wordEntity.setChineseInvisible(true);
                    wordViewModel.updateWords(wordEntity);
                }else{
                    holder.chinese.setVisibility(View.VISIBLE);
                    wordEntity.setChineseInvisible(false);
                    wordViewModel.updateWords(wordEntity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    static class WordViewHolder extends RecyclerView.ViewHolder { //加上static防止内存泄漏
        private TextView number;
        private TextView english;
        private TextView chinese;
        private Switch aSwitch;

        WordViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.textViewNumber);
            english = itemView.findViewById(R.id.textViewEnglish);
            chinese = itemView.findViewById(R.id.textViewChinese);
            aSwitch = itemView.findViewById(R.id.chinese_invisible);
        }
    }
}
