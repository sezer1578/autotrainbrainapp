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
public interface MemberDao {
   @Query("SELECT * from family_member ORDER BY name ASC")
    LiveData<List<Member>> getAlphabetizedMembers();

   @Insert (onConflict = OnConflictStrategy.IGNORE)
     void insert(Member name);

    @Query("DELETE FROM family_member")
    void deleteAll();

    @Query("SELECT * from family_member LIMIT 1")
    Member[] getAnyMember();

    @Delete
     void deleteMember(Member member);

    @Query("SELECT * from family_member ORDER BY name ASC")
    LiveData<List<Member>> getAllMembers();

    @Update
     void updateMember(Member member);

    @Query("Select * from family_member WHERE uId=:id")
    Member getMember(Integer id);


}
