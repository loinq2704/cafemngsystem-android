package com.loinq.cafemngsystem.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.loinq.cafemngsystem.db.dto.OrderWithUserWithOrderDetail;
import com.loinq.cafemngsystem.db.entity.Order;

import java.util.List;

@Dao
public interface OrderDao {
    @Insert
    void insert(Order order);

    @Query("SELECT * FROM order_table")
    LiveData<List<Order>> getAllOrders();

    @Query("SELECT * FROM order_table WHERE user = :userId")
    @Transaction
    LiveData<List<OrderWithUserWithOrderDetail>> getOrderByUser(int userId);
}

