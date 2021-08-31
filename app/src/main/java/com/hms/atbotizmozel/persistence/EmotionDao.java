package com.hms.atbotizmozel.persistence;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EmotionDao {
    @Query("SELECT * from family_emotion ORDER BY EmotionName ASC")
    LiveData<List<Emotion>> getAlphabetizedEmotions();

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insert(Emotion EmotionName);

    @Query("DELETE FROM family_emotion")
    void deleteAll();

    @Query("SELECT * from family_emotion LIMIT 1")
    Emotion[] getAnyEmotion();

    @Delete
    void deleteEmotion(Emotion emotion);

    @Query("SELECT * from family_emotion ORDER BY EmotionName ASC")
    LiveData<List<Emotion>> getAllEmotions();

    @Update
    void updateEmotion(Emotion emotion);

    @Query("Select * from family_emotion WHERE uId=:id")
    Emotion getEmotion(Integer id);


}
