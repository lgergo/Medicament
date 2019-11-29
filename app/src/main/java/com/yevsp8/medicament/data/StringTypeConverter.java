package com.yevsp8.medicament.data;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class StringTypeConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<String> stringToList(String data) {
        if (data == null) {
            return null;
        }
        Type listType = new TypeToken<List<String>>() {}.getType();
        List<String> itemList=gson.fromJson(data,listType);

        return itemList;
    }

    @TypeConverter
    public static String listToString(List<String> list) {
        if(list==null)
            return null;
        Type listType = new TypeToken<List<String>>() {}.getType();
        String json=gson.toJson(list,listType);

        return json;
    }
}
