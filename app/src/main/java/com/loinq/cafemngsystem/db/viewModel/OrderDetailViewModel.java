package com.loinq.cafemngsystem.db.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.loinq.cafemngsystem.db.entity.OrderDetail;
import com.loinq.cafemngsystem.db.repository.OrderDetailRepository;

import java.util.List;

public class OrderDetailViewModel extends AndroidViewModel {

    private OrderDetailRepository mRepository;
    private final LiveData<List<OrderDetail>> mAllOrderDetails;

    public OrderDetailViewModel(Application application) {
        super(application);
        mRepository = new OrderDetailRepository(application);
        mAllOrderDetails = mRepository.getAllOrderDetails();
    }

    public LiveData<List<OrderDetail>> getAllOrderDetails() {
        return mAllOrderDetails;
    }

    public long insert(OrderDetail orderDetail) {
        return mRepository.insert(orderDetail);
    }

    public LiveData<OrderDetail> getOrderDetailById(int detailId) {
        return mRepository.getOrderDetailById(detailId);
    }
}
