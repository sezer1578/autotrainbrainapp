package com.hms.atbotizmozel.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScoreDao {
    @Query("SELECT * from family_score ORDER BY date ASC")
    LiveData<List<Score>> getAlphabetizedScore();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Score uId);

    @Query("DELETE FROM family_score")
    void deleteAll();

    @Query("SELECT * from family_score LIMIT 1")
    Score[] getAnyScore();

    @Delete
    void deleteScore(Score score);

    @Query("SELECT * from family_score ORDER BY uId ASC")
    LiveData<List<Score>> getAllScore();

    @Query("Select * from family_score WHERE uId=:id")
    Score getScore(Integer id);
}
