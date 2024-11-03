package com.loinq.cafemngsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.loinq.cafemngsystem.db.viewModel.OrderViewModel;
import com.loinq.cafemngsystem.dbo.OrderDto;

public class CheckoutActivity extends AppCompatActivity {

    private EditText txtAddress;
    private EditText txtPhone;
    private EditText txtNote;
    private Button btnCheckout;

    private Gson gson;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private OrderViewModel mOrderViewModel;

    private void bindingView() {
        txtAddress = findViewById(R.id.etAddress);
        txtPhone = findViewById(R.id.etPhone);
        txtNote = findViewById(R.id.etNote);
        btnCheckout = findViewById(R.id.buttonPlaceOrder);

        gson = new Gson();
        sharedPreferences = getSharedPreferences("cafemngsystem", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mOrderViewModel = new ViewModelProvider(this).get(OrderViewModel.class);
    }

    ;

    private void bindingAction() {
        btnCheckout.setOnClickListener(this::onBtnCheckoutClick);
    }

    private void onBtnCheckoutClick(View view) {
        String orderDtoJson = sharedPreferences.getString("orderDto", "");
        OrderDto orderDto = gson.fromJson(orderDtoJson, OrderDto.class);
        mOrderViewModel.insert(orderDto);
        orderDto.setOrderDetails(null);
        editor.putString("orderDto", gson.toJson(orderDto));
        editor.apply();
        Intent intent = new Intent(this, OrderHistoryActivity.class);
        startActivity(intent);
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Checkout");
        setContentView(R.layout.activity_checkout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }
}