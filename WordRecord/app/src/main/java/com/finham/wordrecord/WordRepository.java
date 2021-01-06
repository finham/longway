package com.finham.wordrecord;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * User: Fin
 * Date: 2020/2/4
 * Time: 16:14
 */
class WordRepository {
    private LiveData<List<WordEntity>> listLiveData;
    private WordDao wordDao;

    WordRepository(Context context) {
        WordDatabase wordDatabase = WordDatabase.getInstance(context.getApplicationContext());
        wordDao = wordDatabase.getWordDao();
        listLiveData = wordDao.getAllWords();
    }

    LiveData<List<WordEntity>> getListLiveData() {
        return listLiveData;
    }

    void insertWords(WordEntity... wordEntities) {
        new InsetTask(wordDao).execute(wordEntities);
    }

    void updateWords(WordEntity... wordEntities) {
        new UpdateTask(wordDao).execute(wordEntities);
    }

    void deleteWords(WordEntity... wordEntities) {
        new DeleteTask(wordDao).execute(wordEntities);
    }

    void clearWords() {
        new ClearTask(wordDao).execute();
    }

    LiveData<List<WordEntity>> findWordsWithPattern(String pattern) {
        return wordDao.findWordsWithPattern("%" + pattern + "%");
    }

    //This class should be static or leaks might occur.
    static class InsetTask extends AsyncTask<WordEntity, Void, Void> {
        private WordDao wordDao;

        InsetTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(WordEntity... wordEntities) {
            wordDao.insertWord(wordEntities);
            return null;
        }
    }

    static class UpdateTask extends AsyncTask<WordEntity, Void, Void> {
        private WordDao wordDao;

        UpdateTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(WordEntity... wordEntities) {
            wordDao.updateWords(wordEntities);
            return null;
        }
    }

    static class DeleteTask extends AsyncTask<WordEntity, Void, Void> {
        private WordDao wordDao;

        DeleteTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(WordEntity... wordEntities) {
            wordDao.deleteWords(wordEntities);
            return null;
        }
    }

    static class ClearTask extends AsyncTask<Void, Void, Void> {
        private WordDao wordDao;

        ClearTask(WordDao wordDao) {
            this.wordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            wordDao.deleteAllWords();
            return null;
        }
    }
}
