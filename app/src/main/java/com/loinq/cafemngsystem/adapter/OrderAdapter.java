package com.loinq.cafemngsystem.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loinq.cafemngsystem.OrderDetailActivity;
import com.loinq.cafemngsystem.R;
import com.loinq.cafemngsystem.dbo.OrderDto;

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

        private void bindingView(View itemView) {
            txtIndex = itemView.findViewById(R.id.textViewOrderIndex);
            txtStatus = itemView.findViewById(R.id.textViewOrderStatus);
            txtDate = itemView.findViewById(R.id.textViewOrderDate);

        }

        private void bindingAction() {
            itemView.setOnClickListener(this::onClickItem);
        }

        private void onClickItem(View view) {
            Intent intent = new Intent(view.getContext(), OrderDetailActivity.class);
            OrderDto orderDto = orderDtoList.get(getAdapterPosition());
            intent.putExtra("orderDto", orderDto);
            view.getContext().startActivity(intent);
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

