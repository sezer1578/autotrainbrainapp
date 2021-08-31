package com.hms.atbotizmozel.persistencehelpers;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.hms.atbotizmozel.persistence.Score;
import com.hms.atbotizmozel.persistence.ScoreDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ScoreRepository {
    private ScoreDao mScoreDao;
    private LiveData<List<Score>> mAllScore;
    private LiveData<Score> mScore;


    public ScoreRepository(Application application){
        ScoreDatabase db = ScoreDatabase.getDatabase(application);
        mScoreDao = db.scoreDao();
        mAllScore = mScoreDao.getAllScore();
    }


    LiveData<List<Score>> getAllScore() { return mAllScore; }

    public void insert (Score score){
        new ScoreRepository.insertAsyncTask(mScoreDao).execute(score);
    }

    public void deleteAll()  { new ScoreRepository.deleteAllScoreAsyncTask(mScoreDao).execute(); }

    public void deleteScore(Score score)  { new ScoreRepository.deleteScoreAsyncTask(mScoreDao).execute(score); }

    public Score getScore(Integer id){
        AsyncTask<Integer, Void, Score> asyncTask = new ScoreRepository.getScoreAsyncTask(mScoreDao, id).execute(id);
        try {
            return asyncTask.get();
        }catch (ExecutionException e){
            e.printStackTrace();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    private static class insertAsyncTask extends AsyncTask<Score, Void, Void>{
        private ScoreDao mAsyncTaskDao;
        insertAsyncTask(ScoreDao dao){
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Score... params){
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllScoreAsyncTask extends AsyncTask<Void, Void, Void> {
        private ScoreDao mAsyncTaskDao;

        deleteAllScoreAsyncTask(ScoreDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }


    private static class deleteScoreAsyncTask extends AsyncTask<Score, Void, Void> {
        private ScoreDao mAsyncTaskDao;

        deleteScoreAsyncTask(ScoreDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Score... params) {
            mAsyncTaskDao.deleteScore(params[0]);
            return null;
        }
    }

    private static class getScoreAsyncTask extends AsyncTask<Integer, Void, Score> {
        private ScoreDao mAsyncTaskDao;
        private Integer id;

        getScoreAsyncTask(ScoreDao dao, Integer id) {
            mAsyncTaskDao = dao;
            this.id = id;
        }

        @Override
        protected Score doInBackground(final Integer... params) {
            return mAsyncTaskDao.getScore(params[0]);

        }
    }
}
