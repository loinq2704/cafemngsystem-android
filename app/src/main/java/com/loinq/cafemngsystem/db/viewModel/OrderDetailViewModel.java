package com.loinq.cafemngsystem.db.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.loinq.cafemngsystem.db.dto.OrderDetailWithDrink;
import com.loinq.cafemngsystem.db.entity.OrderDetail;
import com.loinq.cafemngsystem.db.entity.enum1.Size;
import com.loinq.cafemngsystem.db.entity.enum1.Topping;
import com.loinq.cafemngsystem.db.repository.OrderDetailRepository;
import com.loinq.cafemngsystem.dbo.DrinkDto;
import com.loinq.cafemngsystem.dbo.OrderDetailDto;

import java.util.List;

public class OrderDetailViewModel extends AndroidViewModel {

    private OrderDetailRepository mRepository;
    private final LiveData<List<OrderDetailWithDrink>> mAllOrderDetails;

    public OrderDetailViewModel(Application application) {
        super(application);
        mRepository = new OrderDetailRepository(application);
        mAllOrderDetails = mRepository.getAllOrderDetails();
    }

    public LiveData<List<OrderDetailWithDrink>> getAllOrderDetails() {
        return mAllOrderDetails;
    }

    public long insert(OrderDetail orderDetail) {
        return mRepository.insert(orderDetail);
    }

    public void update(OrderDetailDto orderDetailDto, int orderId) {
        int id = orderDetailDto.getId();;
        DrinkDto drink = orderDetailDto.getDrink();;
        int quantity = orderDetailDto.getQuantity();;
        Size size = orderDetailDto.getSize();
        Topping topping = orderDetailDto.getTopping();
        OrderDetail orderDetail = new OrderDetail(quantity, size, topping, orderId);
        orderDetail.setId(id);
        mRepository.update(orderDetail);
    }

    public void delete(OrderDetailDto orderDetailDto) {
        int id = orderDetailDto.getId();
        mRepository.delete(id);
    }

    public LiveData<OrderDetailWithDrink> getOrderDetailById(int detailId) {
        return mRepository.getOrderDetailById(detailId);
    }
}
