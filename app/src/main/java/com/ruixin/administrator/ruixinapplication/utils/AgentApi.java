package com.ruixin.administrator.ruixinapplication.utils;

import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 作者：Created by ${李丽} on 2018/3/20.
 * 邮箱：543815830@qq.com
 * 网络请求工具类
 */

public class AgentApi {
    static List<Cookie> cookiesList;

    static Request request;
    static HttpUrl Url;
    //初始化Cookie管理器
   static CookieJar cookieJar = new CookieJar() {
        //Cookie缓存区
        private final Map<String, List<Cookie>> cookiesMap = new HashMap<String, List<Cookie>>();
        @Override
        public void saveFromResponse(HttpUrl arg0, List<Cookie> arg1) {

            //移除相同的url的Cookie
            String host = arg0.host();
            List<Cookie> cookiesList = cookiesMap.get(host);
            if (cookiesList != null){
                cookiesMap.remove(host);
            }
            //再重新天添加
            cookiesMap.put(host, arg1);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl arg0) {
            cookiesList = cookiesMap.get(arg0.host());
            //注：这里不能返回null，否则会报NULLException的错误。
            //原因：当Request 连接到网络的时候，OkHttp会调用loadForRequest()
            return cookiesList != null ? cookiesList : new ArrayList<Cookie>();
        }
    };
    public static class RetryInterceptor implements Interceptor {
        private static final String TAG = "RetryInterceptor";

        private int maxRetry =3;//最大重试次数

        //    延迟
        private long delay = 3000;
        //    叠加延迟
        private long increaseDelay = 5000;

//    private Deque<RetryWrapper> retryWrapperDeque = new ArrayDeque<>();

        public RetryInterceptor() {
        }

        public RetryInterceptor(int maxRetry) {
            this.maxRetry = maxRetry;
        }

        public RetryInterceptor(int maxRetry, long delay) {
            this.maxRetry = maxRetry;
            this.delay = delay;
        }

        public RetryInterceptor(int maxRetry, long delay, long increaseDelay) {
            this.maxRetry = maxRetry;
            this.delay = delay;
            this.increaseDelay = increaseDelay;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            RetryWrapper retryWrapper = proceed(chain);

            while (retryWrapper.isNeedReTry()) {
                retryWrapper.retryNum++;
                //Logcat.d().tag(TAG_BAODA).tag(TAG).format("url= %s", retryWrapper.request.url().toString()).msg("retryNum= " + retryWrapper.retryNum).out();
                try {
                    Thread.sleep(delay + (retryWrapper.retryNum - 1) * increaseDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                proceed(chain, retryWrapper.request, retryWrapper);
            }
            return retryWrapper.response == null ? chain.proceed(chain.request()) : retryWrapper.response;
        }

        private RetryWrapper proceed(Chain chain) throws IOException {
            Request request = chain.request();
            RetryWrapper retryWrapper = new RetryWrapper(request, maxRetry);

            proceed(chain, request, retryWrapper);

            return retryWrapper;
        }

        private void proceed(Chain chain, Request request, RetryWrapper retryWrapper) throws IOException {
            try {
                Response response = chain.proceed(request);
                retryWrapper.setResponse(response);
            } catch (SocketException | SocketTimeoutException e) {
                //e.printStackTrace();
            }
        }

        static class RetryWrapper {
            volatile int retryNum = 0;//假如设置为3次重试的话，则最大可能请求5次（默认1次+3次重试 + 最后一次默认）
            Request request;
            Response response;
            private int maxRetry;

            public RetryWrapper(Request request, int maxRetry) {
                this.request = request;
                this.maxRetry = maxRetry;
            }

            public void setResponse(Response response) {
                this.response = response;
            }

            Response response() {
                return this.response;
            }

            Request request() {
                return this.request;
            }

            public boolean isSuccessful() {
                return response != null && response.isSuccessful();
            }

            public boolean isNeedReTry() {
                return !isSuccessful() && retryNum < maxRetry;
            }

            public void setRetryNum(int retryNum) {
                this.retryNum = retryNum;
            }

            public void setMaxRetry(int maxRetry) {
                this.maxRetry = maxRetry;
            }
        }
    }

    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
         //   .connectTimeout(5000L, TimeUnit.MILLISECONDS)
        //    .readTimeout(5000L, TimeUnit.MILLISECONDS)
          //  .addInterceptor(new RetryInterceptor())
            .cookieJar(cookieJar)
            .build();
    public static String doGet(String url){
    String result ="";
    Log.e("tag","url="+url);
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .build();
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie","sflag=1")
                .build();

       Response response = null;
    try {
        response = client.newCall(request).execute();
        result=response.body().string();
       // Log.e("result","result="+result);
    } catch (IOException e) {
        e.printStackTrace();
    }
    return result;

    }


    public static String dopost( String url, HashMap<String, String> params) {
        String result = "";
        Log.e("tag","结果长度"+result.length());
        Log.e("tag","url="+url+params);
        //直接将HashMap转换成键值对
        FormBody.Builder mFormBodyBuild = new FormBody.Builder();
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                mFormBodyBuild.add(key, value);
            }
        }
        FormBody formBody = mFormBodyBuild.build();
        request = new Request.Builder()
                .url(url)
                .addHeader("Cookie","sflag=1")
                .post(formBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if(response.isSuccessful()){
                Headers headers = response.headers();
                Url = request.url();
                //获取头部的Cookie,注意：可以通过Cooke.parseAll()来获取
                List<Cookie> cookies = Cookie.parseAll(Url, headers);
                Log.e("我获取到的cookie是",""+cookies);
                //防止header没有Cookie的情况
                if (cookies != null){
                    //存储到Cookie管理器中
                    okHttpClient.cookieJar().saveFromResponse(Url, cookies);//这样就将Cookie存储到缓存中了
                }
                result=response.body().string();
                Log.e("我获取到的result是",""+result);
                Log.e("tag","结果长度"+result.length());
                System.out.println(result);
                cookiesList= okHttpClient.cookieJar().loadForRequest(Url);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
    public static String dopost2(String url, HashMap<String, String> params) {

        String result = "";
        String cookies = "";
        List<String>cookie=new ArrayList<>();
        cookie.add("sflag=1");
        if(cookiesList!=null&&cookiesList.size()>1) {
            //获取需要提交的CookieStr
            Log.e("请求添加的cookie名字是", "" + cookiesList);
            String cookiename = cookiesList.get(0).name();
            Log.e("请求添加的cookie名字是", "" + cookiename);
            String cookievalue = cookiesList.get(0).value();
            Log.e("请求添加的cookie值是", "" + cookievalue);
            cookie.add(cookiename + "=" + cookievalue);
             cookies=cookiesList.toString();
        }

        Log.e("请求添加的cookie值是",""+cookie);
        FormBody.Builder mFormBodyBuild = new FormBody.Builder();
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                mFormBodyBuild.add(key, value);
            }
        }
        FormBody formBody = mFormBodyBuild.build();
        Request attentionRequest = new Request.Builder()
                .url(url)
                .addHeader("Cookie",cookies)
               // .addHeader("Cookie",cookie.get(1))
                .post(formBody)
                .build();
        Call attentionCall = okHttpClient.newCall(attentionRequest);
        try {
            //连接网络
            Response attentionResponse = attentionCall.execute();
            if (attentionResponse.isSuccessful()){
                //获取返回的数据
                result = attentionResponse.body().string();
                Log.e("result",""+result);
                Log.e("tag","结果长度"+result.length());
                //测试
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return result;
       
    }
    public static String dopost3( String url, HashMap<String, String> params) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .build();
        String result = "";
        Log.e("tag","url="+url+params);
        //直接将HashMap转换成键值对
        FormBody.Builder mFormBodyBuild = new FormBody.Builder();
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                mFormBodyBuild.add(key, value);
            }
        }
        FormBody formBody = mFormBodyBuild.build();
        request = new Request.Builder()
                .url(url)
                .addHeader("cookie","sflag=1")
                .post(formBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                result=response.body().string();
                Log.e("tag","结果长度"+result.length());
                System.out.println(result);
            }

        } catch (Exception e) {
            Log.e("tag",""+  e);
            e.printStackTrace();
        }
        return result;

    }
    //文件，参数同时上传
    public static String dopost4( String url, HashMap<String, String> params,String name,File file) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .build();
        String result = "";
        Log.e("tag","url="+url+params+file);
        //直接将HashMap转换成键值对
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Builder multipartBody = new MultipartBody.Builder();
        multipartBody .setType(MultipartBody.FORM);
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                multipartBody.addFormDataPart(key, value);
            }
        }
        multipartBody.addFormDataPart(name, String.valueOf(file),fileBody);
        MultipartBody formBody = multipartBody.build();
        request = new Request.Builder()
                .url(url)
                .addHeader("cookie","sflag=1")
                .post(formBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                result=response.body().string();
                System.out.println(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
}


