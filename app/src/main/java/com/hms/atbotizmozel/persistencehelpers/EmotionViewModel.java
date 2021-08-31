package com.hms.atbotizmozel.persistencehelpers;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hms.atbotizmozel.persistence.Emotion;

import java.util.List;

public class EmotionViewModel extends AndroidViewModel {
    private EmotionRepository mRepository;
    private LiveData<List<Emotion>> mAllEmotion;

    public EmotionViewModel(Application application){
        super(application);
        mRepository= new EmotionRepository(application);
        mAllEmotion = mRepository.getAllEmotion();
    }

    public LiveData<List<Emotion>> getmAllEmotion(){
        return mAllEmotion;
    }

    public void insert(Emotion emotion){
        mRepository.insert(emotion);
    }

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteEmotion(Emotion emotion) {mRepository.deleteEmotion(emotion);}

    public void updateemotion(Emotion emotion) {mRepository.updateEmotion(emotion);}

    public Emotion getEmotion(Integer id){
        return mRepository.getEmotion(id);
    }


}
