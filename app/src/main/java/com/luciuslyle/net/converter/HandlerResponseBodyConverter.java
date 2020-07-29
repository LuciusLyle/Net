package com.luciuslyle.net.converter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;


final class HandlerResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;


    HandlerResponseBodyConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
        gson = null;
    }

    HandlerResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        // 这里就是对返回结果进行处理

        //方式1
        if (gson != null) {
            JsonReader jsonReader = gson.newJsonReader(value.charStream());
            return adapter.read(jsonReader);
        }
        //方式2
        String jsonString = value.string();
        return adapter.fromJson(jsonString);

    }
}