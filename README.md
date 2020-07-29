# implementation 'com.github.LuciusLyle:Net:0.0.2'
https://jitpack.io/private#subscribe

1:  初始化配置：HttpConfig

        HttpConfig config = new HttpConfig()
                .setBaseUrl("")// base url
                .setRetrofitConfiguration(new Interceptor() {
                      @Override
                      public Response intercept(Chain chain) throws IOException {
                          Request original = chain.request();
                          Request.Builder requestBuilder = original.newBuilder()
                              .addHeader("user-agent", "android")
                              .addHeader("content-type", "application/json;charset:utf-8")
                              .addHeader("language", BaseSP.getInstance().getString("language"))//语言类型
                              .addHeader("token", BaseSP.getInstance().getString("token"))
                              .header("device", "device")//设备信息
                              .header("version", "version")//app版本
                              .header("apiVersion", "apiVersion")//api版本
                              .header("channelId", "channelId")//渠道
                              Request request = requestBuilder.build();
                              return chain.proceed(request);
                      }
                })
                 .setOkhttpConfiguration()//Customize Okhttp Config
        //初始化        
        BaseHttp.getInstance().init(config);
        
         //全局修改BaseRul
         BaseHttp.getInstance().setHost("");
        /**
         * 请求返回过滤
         */
        BaseHttp.getInstance().setRequestCallFilter(new RequestCallFilter() {
            @Override
            public void onNext(Object o) {
                if (o instanceof ResponseBody) {

                } else if (o instanceof HttpResult) {
                    
                }
            }
        }); 
        
2:  a:  rx+Retrofit RetrofitServiceManager(RetrofitService 管理)

        自定义ApiService,多模块多 ApiService
    b:  获取Observable
        Observable observable = RetrofitServiceManager.getInstance().obtainRetrofitService(ApiService.class);//获取 RetrofitService
    c:  获取Observer
        提供两种转换方式 1:Observable<ResponseBody>(返回JSONObject) 2:Observable<T>(返回泛型T)
        可以自定义HttpConfig 配置转换方式

3:  请求返回过滤(支持 ResponseBody  和 泛型 类)

        BaseHttp.getInstance().setRequestCallFilter(new RequestCallFilter() {
            @Override
            public void onNext(Object o) {
                if (o instanceof ResponseBody) {

                } else if (o instanceof HttpResult) {
                    
                }
            }
        });


4: 使用示例

        RetrofitHelper.qurest(Observable observable, RequestCallback callback);//rx线程管理,observable与自定义Observer类
        RetrofitHelper.qurest(RetrofitServiceManager.getInstance().obtainRetrofitService(UserService.class).executeGetEntity("path"),
                new RequestCallback<HttpResult<List<UserEntity>>>() {
                 @Override
                        public void onError(Throwable e) {
                            //super.onError(e);
                             Log.e("xxx","rx异常");
                        } 
                    @Override
                    public void onFailure(Throwable e, String msg) {
                        Log.e("xxx","异常解析错误分类："+msg);
                    }
                    @Override
                    public void onResponse(HttpResult<List<UserEntity>> response) {
                        super.onResponse(response);
                        Log.e("xxx","实体："+response.getData().get(0).getName());
                    }
                });

        RetrofitHelper.qurest(RetrofitServiceManager.getInstance().obtainRetrofitService(UserService.class).executeGet("home/content"),  
                new RequestCallback<ResponseBody>() {
            @Override
            public void onFailure(Throwable e, String msg) {
                Log.e("xxx","异常解析错误分类："+msg);
            }
            @Override
            public void onResponse(JSONObject response) {
                Log.e("xxx","Gson:："+response.optString("message"));
            }
        });
