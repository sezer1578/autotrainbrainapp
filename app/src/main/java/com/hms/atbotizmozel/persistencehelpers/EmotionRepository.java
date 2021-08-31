package com.hms.atbotizmozel.persistencehelpers;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.hms.atbotizmozel.persistence.Emotion;
import com.hms.atbotizmozel.persistence.EmotionDao;


import java.util.List;
import java.util.concurrent.ExecutionException;

public class EmotionRepository {
    private EmotionDao mEmotionDao;
    private LiveData<List<Emotion>> mAllEmotion;
    private LiveData<Emotion> mEmotion;


    public EmotionRepository(Application application){
        EmotionDatabase db = EmotionDatabase.getDatabase(application);
        mEmotionDao = db.emotionDao();
        mAllEmotion = mEmotionDao.getAllEmotions();
    }


    LiveData<List<Emotion>> getAllEmotion() { return mAllEmotion; }

    public void insert (Emotion emotion){
        new insertAsyncTask(mEmotionDao).execute(emotion);
    }

    public void updateEmotion(Emotion emotion){ new updateEmotionsAsyncTask(mEmotionDao).execute(emotion);}

    public void deleteAll()  { new deleteAllEmotionsAsyncTask(mEmotionDao).execute(); }

    public void deleteEmotion(Emotion emotion)  { new deleteEmotionAsyncTask(mEmotionDao).execute(emotion); }

    public Emotion getEmotion(Integer id){
        AsyncTask<Integer, Void, Emotion> asyncTask = new getEmotionsAsyncTask(mEmotionDao, id).execute(id);
        try {
            return asyncTask.get();
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }



    private static class insertAsyncTask extends AsyncTask<Emotion, Void, Void>{
        private EmotionDao mAsyncTaskDao;
        insertAsyncTask(EmotionDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Emotion... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllEmotionsAsyncTask extends AsyncTask<Void, Void, Void> {
        private EmotionDao mAsyncTaskDao;

        deleteAllEmotionsAsyncTask(EmotionDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }


    private static class deleteEmotionAsyncTask extends AsyncTask<Emotion, Void, Void> {
        private EmotionDao mAsyncTaskDao;

        deleteEmotionAsyncTask(EmotionDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Emotion... params) {
            mAsyncTaskDao.deleteEmotion(params[0]);
            return null;
        }
    }

    private static class updateEmotionsAsyncTask extends AsyncTask<Emotion, Void, Void> {
        private EmotionDao mAsyncTaskDao;

        updateEmotionsAsyncTask(EmotionDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Emotion... params) {
            mAsyncTaskDao.updateEmotion(params[0]);
            return null;
        }
    }

    private static class getEmotionsAsyncTask extends AsyncTask<Integer, Void, Emotion> {
        private EmotionDao mAsyncTaskDao;
        private Integer id;

        getEmotionsAsyncTask(EmotionDao dao, Integer id) {
            mAsyncTaskDao = dao;
            this.id = id;
        }

        @Override
        protected Emotion doInBackground(final Integer... params) {
            return mAsyncTaskDao.getEmotion(params[0]);

        }
    }


}
