package com.loinq.cafemngsystem.db.viewModel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.loinq.cafemngsystem.db.entity.User;
import com.loinq.cafemngsystem.db.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mRepository;

    private final LiveData<List<User>> mAllUsers;

    public UserViewModel (Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mAllUsers = mRepository.getAll();
    }

    public LiveData<List<User>> getAllUsers() { return mAllUsers; }

    public void insert(User word) { mRepository.insert(word); }

    public LiveData<User> getByUsername(String username) {
        return mRepository.getByUsername(username);
    }
}
