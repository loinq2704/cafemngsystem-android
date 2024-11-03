package com.loinq.cafemngsystem.db.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.loinq.cafemngsystem.db.MyRoomDatabase;
import com.loinq.cafemngsystem.db.dao.OrderDetailDao;
import com.loinq.cafemngsystem.db.dto.OrderDetailWithDrink;
import com.loinq.cafemngsystem.db.entity.OrderDetail;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class OrderDetailRepository {
    private OrderDetailDao mOrderDetailDao;

    public OrderDetailRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        mOrderDetailDao = db.orderDetailDao();
    }

    public LiveData<List<OrderDetailWithDrink>> getAllOrderDetails() {
        return mOrderDetailDao.getAllOrderDetails();
    }

    public long insert(OrderDetail orderDetail) {
        Callable<Long> insertCallable = () -> mOrderDetailDao.insert(orderDetail);
        long rowId = -1;

        Future<Long> future = MyRoomDatabase.databaseWriteExecutor.submit(insertCallable);
        try {
            rowId = future.get();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return rowId;
    }

    public void update(OrderDetail orderDetail) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> mOrderDetailDao.update(orderDetail));
    }

    public void delete(int orderDetail) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> mOrderDetailDao.delete(orderDetail));
    }

    public LiveData<OrderDetailWithDrink> getOrderDetailById(int detailId) {
        return mOrderDetailDao.getOrderDetailById(detailId);
    }
}
