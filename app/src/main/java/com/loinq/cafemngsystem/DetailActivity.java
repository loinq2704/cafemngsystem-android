package com.loinq.cafemngsystem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.loinq.cafemngsystem.db.entity.OrderDetail;
import com.loinq.cafemngsystem.db.entity.enum1.Size;
import com.loinq.cafemngsystem.db.entity.enum1.Topping;
import com.loinq.cafemngsystem.db.viewModel.OrderDetailViewModel;
import com.loinq.cafemngsystem.dto.DrinkDto;
import com.loinq.cafemngsystem.dto.OrderDto;

import java.text.DecimalFormat;

public class DetailActivity extends AppCompatActivity {

    private static final String APP_NAME = "cafemngsystem";

    private OrderDetail orderDetail;

    private int quantity = 1;
    private double price = 0;

    private ImageView imgDink;

    private TextView txtDrinkName;
    private RadioGroup rgSize;
    private RadioButton rdoSmall, rdoMedium, rdoLarge;
    private RadioGroup rgTopping;
    private RadioButton rdoBoba, rdoAlmond, rdoCheese;
    private Button btnMinus, btnPlus, btnAddToCart;

    private TextView txtQuantity;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private OrderDetailViewModel orderDetailViewModel;

    private void bindingView() {
        imgDink = findViewById(R.id.imgDink);
        txtDrinkName = findViewById(R.id.txtDrinkName);
        rgSize = findViewById(R.id.rgSize);
        rdoSmall = findViewById(R.id.rdoSmall);
        rdoMedium = findViewById(R.id.rdoMedium);
        rdoLarge = findViewById(R.id.rdoLarge);
        rgTopping = findViewById(R.id.rgTopping);
        rdoBoba = findViewById(R.id.rdoBoba);
        rdoAlmond = findViewById(R.id.rdoAlmond);
        rdoCheese = findViewById(R.id.rdoCheese);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        txtQuantity = findViewById(R.id.txtQuantity);

        gson = new Gson();
        orderDetailViewModel = new ViewModelProvider(this).get(OrderDetailViewModel.class);
    }

    private void bindingAction() {
        rgSize.setOnCheckedChangeListener(this::onSizeChanged);
        rgTopping.setOnCheckedChangeListener(this::onToppingChanged);
        btnMinus.setOnClickListener(this::onMinusClicked);
        btnPlus.setOnClickListener(this::onPlusClicked);
        btnAddToCart.setOnClickListener(this::onAddToCartClicked);
    }

    private void onAddToCartClicked(View view) {
        orderDetail.setQuantity(quantity);
        long orderDetailId = orderDetailViewModel.insert(orderDetail);
        if(orderDetailId == -1) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            return;
        }
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderDetailId((int) orderDetailId);
        String orderDtoJson = gson.toJson(orderDto);
        editor.putString("orderDto", orderDtoJson);
        editor.apply();
        finish();
    }

    private void onMinusClicked(View view) {
        if (quantity >= 1) {
            quantity--;
            txtQuantity.setText(String.valueOf(quantity));
        }
        
        btnAddToCart.setText("$" + calcalatePrice() + " Cart");
    }

    private void onPlusClicked(View view) {
        quantity++;
        txtQuantity.setText(String.valueOf(quantity));
        
        btnAddToCart.setText("$" + calcalatePrice() + " Cart");
    }

    private void onToppingChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.rdoNone)
            orderDetail.setTopping(Topping.None);
        if (i == R.id.rdoBoba)
            orderDetail.setTopping(Topping.Boba);
        if (i == R.id.rdoAlmond)
            orderDetail.setTopping(Topping.Almond);
        if (i == R.id.rdoCheese)
            orderDetail.setTopping(Topping.Cheese);

        
        btnAddToCart.setText("$" + calcalatePrice() + " Cart");
    }

    private void onSizeChanged(RadioGroup radioGroup, int i) {
        if (i == R.id.rdoSmall)
            orderDetail.setSize(Size.S);
        if (i == R.id.rdoMedium)
            orderDetail.setSize(Size.M);
        if (i == R.id.rdoLarge)
            orderDetail.setSize(Size.L);

        
        btnAddToCart.setText("$" + calcalatePrice() + " Cart");
    }

    private String calcalatePrice(){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedNumber = decimalFormat.format((price + orderDetail.getTopping().getValue()) * quantity * orderDetail.getSize().getValue());
        return formattedNumber;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindingView();
        bindingAction();
        receiveIntent();
    }

    private void receiveIntent() {
        DrinkDto drink = (DrinkDto) getIntent().getSerializableExtra("drink");
        orderDetail = new OrderDetail(drink.getId(), quantity, Size.S, Topping.None);
        imgDink.setImageResource(drink.getImg());
        txtDrinkName.setText(drink.getName());
        price = drink.getPrice();
        txtQuantity.setText(String.valueOf(quantity));
        btnAddToCart.setText("$" + calcalatePrice() + " Cart");
    }
}