package com.group5.cafemngsystem.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.group5.cafemngsystem.db.entity.Drink;

import java.util.List;

@Dao
public interface DrinkDao {
    @Insert
    void insert(Drink... drink);

    @Query("SELECT * FROM drinks")
    LiveData<List<Drink>> getAllDrinks();

    @Query("SELECT * FROM drinks WHERE id = :drinkId")
    LiveData<Drink> getDrinkById(int drinkId);
}
