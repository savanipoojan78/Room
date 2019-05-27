package com.example.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.room.DAO.NoteDao;
import com.example.room.DAtabase.NoteDatabase;
import com.example.room.Entity.Note;

import java.util.List;

public class NoteRepo {
    private NoteDao noteDao;
    private LiveData<List<Note>> allnotes;

    public NoteRepo(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allnotes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new insertAsyncTask(noteDao).execute(note);

    }

    public void update(Note note) {
        new updateAsyncTask(noteDao).execute(note);

    }

    public void delete(Note note) {
        new deleteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNodes(Note node)
    {
        new deleteAllAsyncTask(noteDao).execute();
    }
    public LiveData<List<Note>> getAllnotes(){
        return allnotes;
    }
    private static class insertAsyncTask extends AsyncTask<Note,Void,Void>
    {
       private NoteDao noteDao;
        private insertAsyncTask(NoteDao noteDao)
        {
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }
    private static class updateAsyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao noteDao;
        private updateAsyncTask(NoteDao noteDao)
        {
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }
    private static class deleteAsyncTask extends AsyncTask<Note,Void,Void>
    {
        private NoteDao noteDao;
        private deleteAsyncTask(NoteDao noteDao)
        {
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
    private static class deleteAllAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private NoteDao noteDao;
        private deleteAllAsyncTask(NoteDao noteDao)
        {
            this.noteDao=noteDao;
        }
        @Override
        protected Void doInBackground(Void... Voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

}
