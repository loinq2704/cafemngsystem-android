package com.loinq.cafemngsystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.loinq.cafemngsystem.R;
import com.loinq.cafemngsystem.db.viewModel.DrinkViewModel;
import com.loinq.cafemngsystem.dbo.OrderDetailDto;

import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

    private List<OrderDetailDto> orderItemDtoList;

    public OrderItemAdapter(List<OrderDetailDto> orderItemDtoList) {
        this.orderItemDtoList = orderItemDtoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.order_detai_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetailDto orderItemDto = orderItemDtoList.get(position);

        holder.setData(orderItemDto);
    }

    @Override
    public int getItemCount() {
        return orderItemDtoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgDrink;
        private TextView txtDrinkName;
        private TextView txtDrinkSize;
        private TextView txtDrinkTopping;
        private TextView txtDrinkQuantity;

        private Context context;
        private DrinkViewModel mDrinkViewModel;

        private void bindingView(View itemView) {
            imgDrink = itemView.findViewById(R.id.imageViewDrink);
            txtDrinkName = itemView.findViewById(R.id.textViewDrinkName);
            txtDrinkSize = itemView.findViewById(R.id.textViewDrinkSize);
            txtDrinkTopping = itemView.findViewById(R.id.textViewDrinkToppings);
            txtDrinkQuantity = itemView.findViewById(R.id.textViewDrinkQuantity);

            context = itemView.getContext();
            mDrinkViewModel = new ViewModelProvider((AppCompatActivity) context).get(DrinkViewModel.class);
        }

        private void bindingAction() {
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView(itemView);
            bindingAction();
        }

        public void setData(OrderDetailDto orderItem) {
            imgDrink.setImageResource(orderItem.getDrink().getImg());
            txtDrinkName.setText(orderItem.getDrink().getName());
            txtDrinkSize.setText(orderItem.getSize().toString());
            txtDrinkTopping.setText(orderItem.getTopping().toString());
            txtDrinkQuantity.setText(String.valueOf(orderItem.getQuantity()));
        }
    }
}


