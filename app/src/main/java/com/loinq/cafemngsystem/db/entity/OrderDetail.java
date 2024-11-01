package com.loinq.cafemngsystem.db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.loinq.cafemngsystem.db.entity.enum1.Size;
import com.loinq.cafemngsystem.db.entity.enum1.Topping;

@Entity(tableName = "order_detail")
public class OrderDetail {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int drinkId;
    private int quantity;
    private Size size;
    private Topping topping;

    public OrderDetail(int drinkId, int quantity, Size size, Topping topping) {
        this.drinkId = drinkId;
        this.quantity = quantity;
        this.size = size;
        this.topping = topping;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(int drinkId) {
        this.drinkId = drinkId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Topping getTopping() {
        return topping;
    }

    public void setTopping(Topping topping) {
        this.topping = topping;
    }
}
