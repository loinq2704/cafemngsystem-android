package com.loinq.cafemngsystem.db.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.loinq.cafemngsystem.db.MyRoomDatabase;
import com.loinq.cafemngsystem.db.dao.DrinkDao;
import com.loinq.cafemngsystem.db.entity.Drink;

import java.util.List;

public class DrinkRepository {
    private DrinkDao mDrinkDao;

    public DrinkRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        mDrinkDao = db.drinkDao();
    }

    public LiveData<List<Drink>> getAllDrinks() {
        return mDrinkDao.getAllDrinks();
    }

    public void insert(Drink drink) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            mDrinkDao.insert(drink);
        });
    }

    public LiveData<Drink> getDrinkById(int drinkId) {
        return mDrinkDao.getDrinkById(drinkId);
    }
}
