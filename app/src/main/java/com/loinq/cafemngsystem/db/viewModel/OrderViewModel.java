package com.loinq.cafemngsystem.db.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.loinq.cafemngsystem.db.entity.Order;
import com.loinq.cafemngsystem.db.repository.OrderRepository;

import java.util.List;

public class OrderViewModel extends AndroidViewModel {

    private OrderRepository mRepository;
    private final LiveData<List<Order>> mAllOrders;

    public OrderViewModel(Application application) {
        super(application);
        mRepository = new OrderRepository(application);
        mAllOrders = mRepository.getAllOrders();
    }

    public LiveData<List<Order>> getAllOrders() {
        return mAllOrders;
    }

    public void insert(Order order) {
        mRepository.insert(order);
    }

    public LiveData<Order> getOrderById(int orderId) {
        return mRepository.getOrderById(orderId);
    }
}
