package com.loinq.cafemngsystem.db.helper;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListOrderDetailConverter {
    @TypeConverter
    public static String fromList(List<Integer> list) {
        if (list == null) return null;
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    // Convert String back to List<Integer> when retrieving from Room
    @TypeConverter
    public static List<Integer> toList(String value) {
        if (value == null) return null;
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Integer>>() {}.getType();
        return gson.fromJson(value, listType);
    }
}
