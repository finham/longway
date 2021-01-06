package com.finham.wordrecord;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * User: Fin
 * Date: 2020/2/4
 * Time: 14:59
 */
public class WordViewModel extends AndroidViewModel {
    private WordRepository wordRepository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository=new WordRepository(application);
    }

    LiveData<List<WordEntity>> getList() {
        return wordRepository.getListLiveData();
    }

    void insertWords(WordEntity... wordEntities){
        wordRepository.insertWords(wordEntities);
    }

    void updateWords(WordEntity... wordEntities){
        wordRepository.updateWords(wordEntities);
    }

    void deleteWords(WordEntity... wordEntities){
        wordRepository.deleteWords(wordEntities);
    }

    void clearWords(){
        wordRepository.clearWords();
    }

    LiveData<List<WordEntity>> findWords(String pattern){
        return wordRepository.findWordsWithPattern(pattern);
    }

}
