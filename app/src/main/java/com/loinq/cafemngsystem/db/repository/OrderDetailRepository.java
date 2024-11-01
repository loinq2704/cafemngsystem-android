package com.loinq.cafemngsystem.db.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.loinq.cafemngsystem.db.MyRoomDatabase;
import com.loinq.cafemngsystem.db.dao.OrderDetailDao;
import com.loinq.cafemngsystem.db.entity.OrderDetail;

import java.util.List;

public class OrderDetailRepository {
    private OrderDetailDao mOrderDetailDao;

    public OrderDetailRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        mOrderDetailDao = db.orderDetailDao();
    }

    public LiveData<List<OrderDetail>> getAllOrderDetails() {
        return mOrderDetailDao.getAllOrderDetails();
    }

    public void insert(OrderDetail orderDetail) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            mOrderDetailDao.insert(orderDetail);
        });
    }

    public LiveData<OrderDetail> getOrderDetailById(int detailId) {
        return mOrderDetailDao.getOrderDetailById(detailId);
    }
}
