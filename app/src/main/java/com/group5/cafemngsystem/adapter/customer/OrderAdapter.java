package com.group5.cafemngsystem.adapter.customer;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.group5.cafemngsystem.customer.OrderDetailActivity;
import com.group5.cafemngsystem.R;
import com.group5.cafemngsystem.db.dto.OrderDetailWithDrink;
import com.group5.cafemngsystem.db.viewModel.OrderDetailViewModel;
import com.group5.cafemngsystem.dbo.OrderDetailDto;
import com.group5.cafemngsystem.dbo.OrderDto;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<OrderDto> orderDtoList;

    public OrderAdapter(List<OrderDto> orderDtoList) {
        this.orderDtoList = orderDtoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.order_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDto orderDto = orderDtoList.get(position);

        holder.setData(orderDto);
    }

    @Override
    public int getItemCount() {
        return orderDtoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtIndex;
        private TextView txtStatus;
        private TextView txtDate;

        private AppCompatActivity context;
        private OrderDetailViewModel mOrderDetailViewModel;

        private void bindingView(View itemView) {
            txtIndex = itemView.findViewById(R.id.textViewOrderIndex);
            txtStatus = itemView.findViewById(R.id.textViewOrderStatus);
            txtDate = itemView.findViewById(R.id.textViewOrderDate);

            context = (AppCompatActivity) itemView.getContext();
            mOrderDetailViewModel = new ViewModelProvider(context).get(OrderDetailViewModel.class);
        }

        private void bindingAction() {
            itemView.setOnClickListener(this::onClickItem);
        }

        private void onClickItem(View view) {
            OrderDto orderDto = orderDtoList.get(getAdapterPosition());
            mOrderDetailViewModel.getOrderDetailListByOrderId(orderDto.getId()).observe(context, orderDetailDto -> {
                Intent intent = new Intent(view.getContext(), OrderDetailActivity.class);
                List<OrderDetailDto> orderDetailList = new ArrayList<>();
                for(OrderDetailWithDrink orderDetail : orderDetailDto) {
                    orderDetailList.add(new OrderDetailDto(orderDetail));
                }
                orderDto.setOrderDetails(orderDetailList);
                intent.putExtra("orderDto", orderDto);
                view.getContext().startActivity(intent);
            });
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView(itemView);
            bindingAction();
        }

        public void setData(OrderDto orderDto) {
            int position = getAdapterPosition();
            txtIndex.setText("Order #" + (position + 1));
            txtStatus.setText(orderDto.getOrderStatus().toString());
            txtDate.setText(orderDto.getDate().toString());
        }
    }
}

