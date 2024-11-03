package com.loinq.cafemngsystem.db.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.loinq.cafemngsystem.db.dto.OrderWithUserWithOrderDetail;
import com.loinq.cafemngsystem.db.entity.Order;
import com.loinq.cafemngsystem.db.entity.enum1.OrderStatus;
import com.loinq.cafemngsystem.db.repository.OrderRepository;
import com.loinq.cafemngsystem.dbo.OrderDetailDto;
import com.loinq.cafemngsystem.dbo.OrderDto;

import java.util.ArrayList;
import java.util.Date;
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

    public void insert(OrderDto orderDto) {

        String address = orderDto.getAddress();
        int userId = orderDto.getUser().getId();
        String phone = orderDto.getPhone();
        String note = orderDto.getNote();
        Order order = new Order(new Date(), userId, address, phone, note, OrderStatus.PENDING);
        mRepository.insert(order);
    }

    public LiveData<List<OrderWithUserWithOrderDetail>> getOrderByUser(int userId) {
        return mRepository.getOrderByUser(userId);
    }
}
