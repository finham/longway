package com.finham.wordrecord;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class WordsFragment extends Fragment {
    private static final String VIEW_TYPE = "view_type";
    private static final String IS_USING_CARD = "is_using_card_view";
    private WordViewModel wordViewModel;
    private RecyclerView recyclerView;
    private WordAdapter wordAdapter1, wordAdapter2;
    private LiveData<List<WordEntity>> filteredWords;
    private List<WordEntity> allWords;
    private boolean undoAction;
    private DividerItemDecoration dividerItemDecoration;

    public WordsFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView(); //需要getActionView()
        searchView.setMaxWidth(1000);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { //按了键盘上的确定键/搜索键后就会呼叫该方法
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                String pattern = s.trim();
                filteredWords.removeObservers(getViewLifecycleOwner()); //在调用新的观察者时要把旧的观察者给清除，不然会出现碰撞重叠。不加此句列表会很奇怪
                filteredWords = wordViewModel.findWords(pattern);
                filteredWords.observe(getViewLifecycleOwner(), new Observer<List<WordEntity>>() {
                    @Override
                    public void onChanged(List<WordEntity> wordEntities) {
                        int temp = wordAdapter1.getItemCount();
                        allWords = wordEntities;
//                        wordAdapter1.setWords(wordEntities);
//                        wordAdapter2.setWords(wordEntities);
                        if (temp != wordEntities.size()) {
                            //1.不好的处理：
                            //wordAdapter1.notifyDataSetChanged();
                            //wordAdapter2.notifyDataSetChanged();
                            //解决方法1：
                            //wordAdapter1.notifyItemInserted(0);//表示是在第0个位置插入
                            //解决方法2：
                            wordAdapter1.submitList(wordEntities);
                            wordAdapter2.submitList(wordEntities);
                        }
                    }
                });
                return true;
            }
        });
    }

    /**
     * 菜单栏点击事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clearData:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("Clear Data");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        wordViewModel.clearWords();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.create();
                builder.show();
                break;
            case R.id.switchView:
                SharedPreferences preferences = requireActivity().getSharedPreferences(VIEW_TYPE, Context.MODE_PRIVATE);
                boolean viewType = preferences.getBoolean(IS_USING_CARD, false);
                SharedPreferences.Editor editor = preferences.edit();
                if (viewType) {
                    recyclerView.setAdapter(wordAdapter1);
                    recyclerView.addItemDecoration(dividerItemDecoration);
                    editor.putBoolean(IS_USING_CARD, false);
                } else {
                    recyclerView.setAdapter(wordAdapter2);
                    recyclerView.removeItemDecoration(dividerItemDecoration);
                    editor.putBoolean(IS_USING_CARD, true);
                }
                editor.apply(); //不要忘了提交！
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_words, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        wordViewModel = new ViewModelProvider(requireActivity()).get(WordViewModel.class);
        recyclerView = requireActivity().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        wordAdapter1 = new WordAdapter(false, wordViewModel);
        wordAdapter2 = new WordAdapter(true, wordViewModel);
        recyclerView.setItemAnimator(new DefaultItemAnimator() {
            @Override //右键-Generate-Override Methods
            public void onAnimationFinished(@NonNull RecyclerView.ViewHolder viewHolder) {
                super.onAnimationFinished(viewHolder);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null) {
                    int firstPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    int lastPosition = linearLayoutManager.findLastVisibleItemPosition();
                    for (int i = firstPosition; i <= lastPosition; i++) {
                        WordAdapter.WordViewHolder holder = (WordAdapter.WordViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
                        if (holder != null) {
                            holder.number.setText(String.valueOf(i + 1));
                        }
                    }
                }
            }
        });
        SharedPreferences preferences = requireActivity().getSharedPreferences(VIEW_TYPE, Context.MODE_PRIVATE);
        boolean viewType = preferences.getBoolean(IS_USING_CARD, false);
        dividerItemDecoration = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        if (viewType) { //为true设为2号
            recyclerView.setAdapter(wordAdapter2);
        } else {
            recyclerView.setAdapter(wordAdapter1);
            recyclerView.addItemDecoration(dividerItemDecoration);
        }
        filteredWords = wordViewModel.getList(); //这样会比较安全，也不会出现空指针的现象
        wordViewModel.getList().observe(getViewLifecycleOwner(), new Observer<List<WordEntity>>() {
            @Override
            public void onChanged(List<WordEntity> wordEntities) {
                int temp = wordAdapter1.getItemCount(); //一定要先用一个变量将count存起来,获取当前adapter里面数据的长度
                allWords = wordEntities;
                //if (wordAdapter1.getItemCount() != wordEntities.size()) { //这样写不行!一定要定义一个变量
                if (temp != wordEntities.size()) { //注意是wordEntities先变化的,再引起wordAdapter去更新自己的数据
                    if (temp < wordEntities.size() && !undoAction) { //如果原有数据的长度小于现有数据的长度，那才需要滚动
                        recyclerView.smoothScrollBy(0, -200);
                    }
                    undoAction = false;
                    wordAdapter1.submitList(wordEntities);
                    wordAdapter2.submitList(wordEntities);
                }
                //另一种更新列表写法，在经过第30讲后，很明显如下的这个是全部刷新，类似于notifyDataSetChanged，而且几乎没有改进空间，不要用
                //words=wordEntities;
                //wordAdapter=new WordAdapter(words);
                //recyclerView.setAdapter(wordAdapter);
            }
        });
        FloatingActionButton floatingActionButton = requireActivity().findViewById(R.id.floatingActionButton);//改成局部变量也行。不过不建议
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.action_wordsFragment_to_addFragment);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.START | ItemTouchHelper.END) {
            ////第一个参数DragDirs：允许拖动的方向；第二个参数SwiprDirs：允许滑动的方向。
            // Binary OR 二进制的或运算，将各个flag或起来
            //例如你允许上和下拖动，那么你就将上下或起来 (|：或运算)

            @Override  //onMove是用来移动Item的
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                WordEntity wordFrom = allWords.get(viewHolder.getAdapterPosition());
                WordEntity wordTo = allWords.get(target.getAdapterPosition());
                int idTemp = wordFrom.getId();
                wordFrom.setId(wordTo.getId());
                wordTo.setId(idTemp);
                wordViewModel.updateWords(wordFrom, wordTo);
                //这个更新数据跟插入数据不一样，需要手动通知才会生效
                wordAdapter1.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                wordAdapter2.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //WordEntity wordDelete=filteredWords.getValue().get(viewHolder.getAdapterPosition());
                //Method invocation 'get' may produce 'NullPointerException'.
                //有可能在数据库异步查询过程中getValue还没获取到值，所以这里要处理may be null的清空，所以重新建个变量：allWords
                final WordEntity wordDelete = allWords.get(viewHolder.getAdapterPosition());//你有机会去滑动的话，那么allWords肯定不为空
                wordViewModel.deleteWords(wordDelete);
                //数据库中删除数据，LiveData就会观察到数据被删除了，就会向wordAdapter重新提交列表(submitList)。
                //由于之前用了ListAdapter，那么它就会计算出两个List的差异，就会以一个动画形式删掉一行。再次看到LiveData的强大之处。
                Snackbar.make(requireActivity().findViewById(R.id.wordsFragmentView), "删除了词汇", Snackbar.LENGTH_SHORT)
                        .setAction("撤销", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                undoAction = true;
                                wordViewModel.insertWords(wordDelete);
                            }
                        }).show();
            }

            //在滑动的时候，画出浅灰色背景和垃圾桶图标，增强删除的视觉效
            Drawable icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_delete_black_24dp);
            Drawable background = new ColorDrawable(Color.LTGRAY);

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                View itemView = viewHolder.itemView;
                int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                int iconLeft, iconRight, iconTop, iconBottom;
                int backTop, backBottom, backLeft, backRight;
                backTop = itemView.getTop();
                backBottom = itemView.getBottom();
                iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
                iconBottom = iconTop + icon.getIntrinsicHeight();
                if (dX > 0) {
                    backLeft = itemView.getLeft();
                    backRight = itemView.getLeft() + (int) dX;
                    background.setBounds(backLeft, backTop, backRight, backBottom);
                    iconLeft = itemView.getLeft() + iconMargin;
                    iconRight = iconLeft + icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                } else if (dX < 0) {
                    backRight = itemView.getRight();
                    backLeft = itemView.getRight() + (int) dX;
                    background.setBounds(backLeft, backTop, backRight, backBottom);
                    iconRight = itemView.getRight() - iconMargin;
                    iconLeft = iconRight - icon.getIntrinsicWidth();
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                } else {
                    background.setBounds(0, 0, 0, 0);
                    icon.setBounds(0, 0, 0, 0);
                }
                background.draw(c);
                icon.draw(c);
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(Objects.requireNonNull(getView()).getWindowToken(), 0);
        }
    }
}
