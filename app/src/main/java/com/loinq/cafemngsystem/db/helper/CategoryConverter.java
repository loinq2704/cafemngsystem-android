package com.loinq.cafemngsystem.db.helper;

import androidx.room.TypeConverter;

import com.loinq.cafemngsystem.db.entity.enum1.Category;

public class CategoryConverter {
    @TypeConverter
    public Category toCategory(String category) {
        return category == null ? null : Category.valueOf(category);
    }
    @TypeConverter
    public String toString(Category category) {
        return category == null ? null : category.name();
    }
}
