package com.hms.atbotizmozel.persistencehelpers;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hms.atbotizmozel.persistence.Member;
import com.hms.atbotizmozel.persistence.MemberDao;

@Database(entities = {Member.class}, version = 6, exportSchema = false)
public abstract class MemberDatabase extends RoomDatabase {

    public abstract MemberDao memberDao();

    private static MemberDatabase INSTANCE;

    public static MemberDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MemberDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MemberDatabase.class, "Final_Project")
                            .fallbackToDestructiveMigration()
                            .build();
                } } }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sOnOpenCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
    }};

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MemberDao mDao;
        String[] name = {"Father", "Mother", "Sister"};
        String[] photo = {"Father.PNG", "Mother.PNG", "Sister.PNG"};


        PopulateDbAsync(MemberDatabase db) {
            mDao = db.memberDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created

            if (mDao.getAnyMember().length < 1) {
                for (int i = 0; i <= name.length - 1; i++) {
                    Member member = new Member(name[i], photo[i]);
                    mDao.insert(member);
                }
            }
            return null;
        }
    }


}


