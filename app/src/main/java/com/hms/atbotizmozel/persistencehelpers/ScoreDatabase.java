package com.hms.atbotizmozel.persistencehelpers;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hms.atbotizmozel.persistence.Score;
import com.hms.atbotizmozel.persistence.ScoreDao;

@Database(entities = {Score.class}, version = 24, exportSchema = false)
public abstract class ScoreDatabase extends RoomDatabase {
    public abstract ScoreDao scoreDao();

    private static ScoreDatabase INSTANCE;

    public static ScoreDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ScoreDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ScoreDatabase.class, "Score")
                            .fallbackToDestructiveMigration()
                            .addCallback(sOnOpenCallback)
                            .build();
                } } }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sOnOpenCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new ScoreDatabase.PopulateDbAsync(INSTANCE).execute();
        }};

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ScoreDao mDao;
        String[] date = {"2021-01-01 10:40:20","2021-02-02 11:41:21"};
        String[] time = {"0.24", "1.30"};
        String[] testName = {"emotion","family"};
        String[] result = {"3","5"};


        PopulateDbAsync(ScoreDatabase db) {
            mDao = db.scoreDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created

            if (mDao.getAnyScore().length < 1) {
                for (int i = 0; i <= date.length - 1; i++) {
                    Score score = new Score(date[i], time[i], testName[i], result[i]);
                    mDao.insert(score);
                }
            }
            return null;
        }
    }


}
