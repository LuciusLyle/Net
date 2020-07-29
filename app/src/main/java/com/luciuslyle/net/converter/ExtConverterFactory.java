package com.luciuslyle.net.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 自定义转换 返回数据接受类型
 * 不需要GsonConverterFactory库
 */
public class ExtConverterFactory extends Converter.Factory {

    public static ExtConverterFactory create() {
        return create(new Gson());
    }

    public static ExtConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new ExtConverterFactory(gson);
    }

    private final Gson gson;

    private ExtConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new HandlerResponseBodyConverter<>(adapter);
    }

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        // 在这里对集合进行统一的处理
        // define customer class to converter to list[0]

        return super.stringConverter(type, annotations, retrofit);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new HandlerRequestBodyConverter<>(gson, adapter);
    }

}
