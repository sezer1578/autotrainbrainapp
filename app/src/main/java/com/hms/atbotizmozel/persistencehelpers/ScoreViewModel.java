package com.hms.atbotizmozel.persistencehelpers;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hms.atbotizmozel.persistence.Score;

import java.util.List;

public class ScoreViewModel extends AndroidViewModel {
    private ScoreRepository mRepository;
    private LiveData<List<Score>> mAllScore;

    public ScoreViewModel(Application application){
        super(application);
        mRepository= new ScoreRepository(application);
        mAllScore = mRepository.getAllScore();
    }

    public LiveData<List<Score>> getmAllScore(){
        return mAllScore;
    }

    public void insert(Score score){
        mRepository.insert(score);
    }

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteScore(Score score) {mRepository.deleteScore(score);}

    public Score getScore(Integer id){
        return mRepository.getScore(id);
    }

}
