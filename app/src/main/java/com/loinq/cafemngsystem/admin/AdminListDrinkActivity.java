package com.loinq.cafemngsystem.admin;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loinq.cafemngsystem.R;
import com.loinq.cafemngsystem.adapter.admin.AdminDrinkAdapter;
import com.loinq.cafemngsystem.db.entity.Drink;
import com.loinq.cafemngsystem.db.viewModel.DrinkViewModel;
import com.loinq.cafemngsystem.dbo.DrinkDto;

import java.util.ArrayList;
import java.util.List;

public class AdminListDrinkActivity extends AppCompatActivity {

    private RecyclerView rcvDrink;

    private DrinkViewModel mDrinkViewModel;

    private void bindingView() {
        rcvDrink = findViewById(R.id.recyclerViewDrinks);

        mDrinkViewModel = new ViewModelProvider(this).get(DrinkViewModel.class);
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_drink);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Drink Management");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        initRcvDrink();
    }

    private void initRcvDrink() {
        mDrinkViewModel.getAllDrinks().observe(this, drinks -> {
            List<DrinkDto> drinkDtos = new ArrayList<>();
            for(Drink drink : drinks)
                drinkDtos.add(new DrinkDto(drink));
            AdminDrinkAdapter adapter = new AdminDrinkAdapter(drinkDtos);
            rcvDrink.setAdapter(adapter);
            rcvDrink.setLayoutManager(new GridLayoutManager(this, 1));
        });
    }
}