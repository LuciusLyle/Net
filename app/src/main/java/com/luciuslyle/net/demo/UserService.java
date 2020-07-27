package com.luciuslyle.net.demo;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface UserService {


    @GET
    Observable<HttpResult<List<UserEntity>>> executeGetEntity(@Url String url);
    
    @GET
    Observable<ResponseBody> executeGet(@Url String url);
    @GET
    Observable<ResponseBody> executeGet(@Url String url, @QueryMap Map<String, String> maps);

    @POST("{url}")
    Observable<ResponseBody> executePost(@Path("url") String url);
    @POST
    Observable<ResponseBody> executePost(@Url String url, @QueryMap Map<String, String> maps);

    
    @Multipart
    @POST("{url}")
    Observable<ResponseBody> upLoadFile(
            @Path("url") String url,
            @Part("image\\\\" +"filename=\\"+"image.jpg") RequestBody avatar);
    @POST("{url}")
    Observable<ResponseBody> uploadFiles(
            @Path("url") String url,
            @Path("headers") Map<String, String> headers,
            @Part("filename") String description,
            @PartMap()  Map<String, RequestBody> maps);

    
    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);

}
