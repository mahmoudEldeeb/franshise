package com.franshise.franshise.models.repositry;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.franshise.franshise.models.database.FavDao;
import com.franshise.franshise.models.database.FavDatabase;
import com.franshise.franshise.models.database.FavModel;

import java.util.List;

public class FavRepositry {
    private FavDao noteDao;
    private LiveData<List<FavModel>>allNotes;

    public FavRepositry(Application application) {
        FavDatabase database = FavDatabase.getInstance(application);
        noteDao = database.favDao();
    }

    public Boolean insert(FavModel model) {
        try {

            new InsertNoteAsyncTask(noteDao).execute(model);
            return true;
        }catch (Exception e){return false;}
    }

    public LiveData<List<FavModel>> getAllFav(int id) {
        allNotes = noteDao.getAllFav(id);
        return allNotes;
    }
    private static class InsertNoteAsyncTask extends AsyncTask<FavModel, Void, Void> {
        private FavDao favDao;

        private InsertNoteAsyncTask(FavDao favDao) {
            this.favDao = favDao;
        }

        @Override
        protected Void doInBackground(FavModel... models) {
            try {
                favDao.insert(models[0]);
            }
            catch (Exception e){
                Log.v("nnnnn",e.toString());
            }
            return null;
        }
    }
}
