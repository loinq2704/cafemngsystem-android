package com.loinq.cafemngsystem.db.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.loinq.cafemngsystem.db.entity.Drink;
import com.loinq.cafemngsystem.db.repository.DrinkRepository;

import java.util.List;

public class DrinkViewModel extends AndroidViewModel {

    private DrinkRepository mRepository;
    private final LiveData<List<Drink>> mAllDrinks;

    public DrinkViewModel(Application application) {
        super(application);
        mRepository = new DrinkRepository(application);
        mAllDrinks = mRepository.getAllDrinks();
    }

    public LiveData<List<Drink>> getAllDrinks() {
        return mAllDrinks;
    }

    public void insert(Drink drink) {
        mRepository.insert(drink);
    }

    public LiveData<Drink> getDrinkById(int drinkId) {
        return mRepository.getDrinkById(drinkId);
    }
}
