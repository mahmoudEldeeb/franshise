package com.franshise.franshise.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.franshise.franshise.models.database.FavModel;
import com.franshise.franshise.models.repositry.FavRepositry;

import java.util.List;

public class FavViewModel extends AndroidViewModel {
    MutableLiveData<Boolean>isSaved;
    FavRepositry favRepositry;
    MutableLiveData<List<FavModel>>listMutableLiveData;
public FavViewModel(@NonNull Application application){
    super(application);
     favRepositry=new FavRepositry(application);

}
    public LiveData<List<FavModel>> getFav(int id){
        listMutableLiveData=new MutableLiveData<>();
      return favRepositry.getAllFav(id);
    }

}
