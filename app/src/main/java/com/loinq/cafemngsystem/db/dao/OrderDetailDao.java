package com.loinq.cafemngsystem.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.loinq.cafemngsystem.db.entity.OrderDetail;

import java.util.List;

@Dao
public interface OrderDetailDao {
    @Insert
    void insert(OrderDetail orderDetail);

    @Query("SELECT * FROM order_detail")
    LiveData<List<OrderDetail>> getAllOrderDetails();

    @Query("SELECT * FROM order_detail WHERE id = :detailId")
    LiveData<OrderDetail> getOrderDetailById(int detailId);
}

