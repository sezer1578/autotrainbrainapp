package com.hms.atbotizmozel.persistencehelpers;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.hms.atbotizmozel.persistence.Emotion;
import com.hms.atbotizmozel.persistence.EmotionDao;


@Database(entities = {Emotion.class}, version = 15, exportSchema = false)
public abstract class EmotionDatabase extends RoomDatabase {

    public abstract EmotionDao emotionDao();

    private static EmotionDatabase INSTANCE;

    public static EmotionDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (EmotionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            EmotionDatabase.class, "Emotion")
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

        private final EmotionDao mDao;
        String[] Emotionname = {"Father", "Mother", "Sister"};
        String[] Emotionphoto = {"Father.PNG", "Mother.PNG", "Sister.PNG"};


        PopulateDbAsync(EmotionDatabase db) {
            mDao = db.emotionDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created

            if (mDao.getAnyEmotion().length < 1) {
                for (int i = 0; i <= Emotionname.length - 1; i++) {
                    Emotion emotion = new Emotion(Emotionname[i], Emotionphoto[i]);
                    mDao.insert(emotion);
                }
            }
            return null;
        }
    }


}


