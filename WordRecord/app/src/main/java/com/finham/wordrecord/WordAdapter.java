package com.finham.wordrecord;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * User: Fin
 * Date: 2020/2/9
 * Time: 22:46
 */
public class WordAdapter extends ListAdapter<WordEntity, WordAdapter.WordViewHolder> {
    //private List<WordEntity> words = new ArrayList<>(); // 继承了ListAdapter之后，这个变量也不需要了，
    //因为ListAdapter自带列表，回头在Fragment里面提交一个列表给它就行了
    private boolean isCard;
    private WordViewModel wordViewModel;

    WordAdapter(boolean isCard, WordViewModel wordViewModel) {
        super(new DiffUtil.ItemCallback<WordEntity>() {
            @Override
            public boolean areItemsTheSame(@NonNull WordEntity oldItem, @NonNull WordEntity newItem) { //判断元素
                //按照数据库主键，如果元素相同的话那么是id相同
                return oldItem.getId() == newItem.getId();
            }

            //上面判断完会继续下面这个判断
            @Override
            public boolean areContentsTheSame(@NonNull WordEntity oldItem, @NonNull WordEntity newItem) { //判断内容
                return oldItem.getWord().equals(newItem.getWord()) && oldItem.getMeaning().equals(newItem.getMeaning())
                        && oldItem.isChineseInvisible() == newItem.isChineseInvisible();
            }
        });
        this.isCard = isCard;
        this.wordViewModel = wordViewModel;
    }

    //这是区别于setWords的另一种写法，可参考
    public WordAdapter(List<WordEntity> wordEntities) {
        super(new DiffUtil.ItemCallback<WordEntity>() {
            @Override
            public boolean areItemsTheSame(@NonNull WordEntity oldItem, @NonNull WordEntity newItem) { //判断元素
                //按照数据库主键，如果元素相同的话那么是id相同
                return oldItem.getId() == newItem.getId();
            }

            //上面判断完会继续下面这个判断
            @Override
            public boolean areContentsTheSame(@NonNull WordEntity oldItem, @NonNull WordEntity newItem) { //判断内容
                return oldItem.getWord().equals(newItem.getWord()) && oldItem.getMeaning().equals(newItem.getMeaning())
                        && oldItem.isChineseInvisible() == newItem.isChineseInvisible();
            }
        });
        //this.words = wordEntities; //在此处传递参数初始化
    }

    //void setWords(List<WordEntity> words) { //上面的words都不需要了，这个也就不需要了
    //    this.words = words;
    //}

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView;
        if (isCard) {
            itemView = layoutInflater.inflate(R.layout.cell_card_2, parent, false);
        } else {
            itemView = layoutInflater.inflate(R.layout.cell_normal_2, parent, false);
        }
        final WordViewHolder holder = new WordViewHolder(itemView);
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
                WordEntity wordEntity = (WordEntity) holder.itemView.getTag(R.id.word_for_view_holder);
                if (b) {
                    holder.chinese.setVisibility(View.GONE);
                    wordEntity.setChineseInvisible(true);
                    wordViewModel.updateWords(wordEntity);
                } else {
                    holder.chinese.setVisibility(View.VISIBLE);
                    wordEntity.setChineseInvisible(false);
                    wordViewModel.updateWords(wordEntity);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final WordViewHolder holder, int position) {
        final WordEntity wordEntity = getItem(position);
        holder.itemView.setTag(R.id.word_for_view_holder, wordEntity); //
        holder.number.setText(String.valueOf(position));
        holder.english.setText(wordEntity.getWord());
        holder.chinese.setText(wordEntity.getMeaning());
        //我们希望setChecked()是来初始化一个状态的，不希望初始化的时候会驱动下面的Listener，加上这一句：
        if (wordEntity.isChineseInvisible()) { //如果为true，代表不要显示中文
            holder.chinese.setVisibility(View.GONE);
            holder.aSwitch.setChecked(true);
        } else {
            holder.chinese.setVisibility(View.VISIBLE);
            holder.aSwitch.setChecked(false);
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull WordViewHolder holder) { //当ViewHolder出现在屏幕上的时候也设置一下序号，更保险
        super.onViewAttachedToWindow(holder);
        holder.number.setText(String.valueOf(holder.getAdapterPosition() + 1));
    }

    //    @Override
//    public int getItemCount() {
//        return words.size();
//    }

    static class WordViewHolder extends RecyclerView.ViewHolder { //加上static防止内存泄漏
        TextView number;
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
