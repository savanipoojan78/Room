package com.example.room.DAtabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.room.DAO.NoteDao;
import com.example.room.Entity.Note;

@Database(entities = Note.class,version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;
    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance= Room.databaseBuilder(context,NoteDatabase.class,"note class")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomcallback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomcallback=new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            new PopulatedDb(instance).execute();
            super.onCreate(db);
        }
    };
    private static class PopulatedDb extends AsyncTask<Void,Void,Void>
    {
        private NoteDao noteDao;
        private PopulatedDb(NoteDatabase db)
        {
            noteDao=db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Poojan","Savani",1));
            noteDao.insert(new Note("SAvani","MOm",2));
            noteDao.insert(new Note("Yahoo","Savani",3));

            return null;
        }
    }


}
