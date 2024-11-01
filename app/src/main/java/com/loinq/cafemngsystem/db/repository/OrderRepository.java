package com.loinq.cafemngsystem.db.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.loinq.cafemngsystem.db.MyRoomDatabase;
import com.loinq.cafemngsystem.db.dao.OrderDao;
import com.loinq.cafemngsystem.db.entity.Order;

import java.util.List;

public class OrderRepository {
    private OrderDao mOrderDao;

    public OrderRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        mOrderDao = db.orderDao();
    }

    public LiveData<List<Order>> getAllOrders() {
        return mOrderDao.getAllOrders();
    }

    public void insert(Order order) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            mOrderDao.insert(order);
        });
    }

    public LiveData<Order> getOrderById(int orderId) {
        return mOrderDao.getOrderById(orderId);
    }
}
