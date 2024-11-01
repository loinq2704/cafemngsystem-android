package com.loinq.cafemngsystem.db.helper;

import androidx.room.TypeConverter;

import com.loinq.cafemngsystem.db.entity.enum1.OrderStatus;

public class OrderStatusConverter {
    @TypeConverter
    public static OrderStatus fromString(String value) {
        return value == null ? null : OrderStatus.valueOf(value);
    }

    @TypeConverter
    public static String statusToString(OrderStatus status) {
        return status == null ? null : status.name();
    }
}
