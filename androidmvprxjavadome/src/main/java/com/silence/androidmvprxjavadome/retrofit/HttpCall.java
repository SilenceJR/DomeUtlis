package com.silence.androidmvprxjavadome.retrofit;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @作者: PJ
 * @创建时间: 2017/11/7 / 18:11
 * @描述: 这是一个 HttpCall 类.
 */
public class HttpCall {
    private static final String TAG = HttpCall.class.getSimpleName();
    private static final String BASE_URL = "http://www.duimy.com/";

    private static String mToken;

    private static ApiService apiService;

    public static void setToken(String token) {
        mToken = token;
    }

    /**
     * 每次都要invoke 这个方法不是很繁琐吗？
     *
     * @return
     */
    public static ApiService getApiService() {
        if (apiService == null) {
            //处理没有认证  http 401 Not Authorised
            Authenticator mAuthenticator = new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    if (responseCount(response) >= 2) {
                        // If both the original call and the call with refreshed token failed,it will probably keep failing, so don't try again.
                        return null;
                    }
                    refreshToken();
                    return response.request().newBuilder()
                            .header("Authorization", mToken)
                            .build();
                }
            };

            /**
             * 如果你的 token 是空的，就是还没有请求到 token，比如对于登陆请求，是没有 token 的，
             * 只有等到登陆之后才有 token，这时候就不进行附着上 token。另外，如果你的请求中已经带有验证 header 了，
             * 比如你手动设置了一个另外的 token，那么也不需要再附着这一个 token.
             */
            Interceptor mRequestInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    if (TextUtils.isEmpty(mToken)) {
//                        TOKEN = SharedPreferencesDao.getInstance().getData(SPKey.KEY_ACCESS_TOKEN, "", String.class);
                    }

                    /***
                     * TOKEN == null，Login/Register noNeed Token
                     * noNeedAuth(originalRequest)    refreshToken api request is after log in before log out,but  refreshToken api no need auth
                     */
                    if (TextUtils.isEmpty(mToken) || alreadyHasAuthorizationHeader(originalRequest) || noNeedAuth(originalRequest)) {
                        Response originalResponse = chain.proceed(originalRequest);
                        return originalResponse.newBuilder()
                                //get http request progress,et download app
                                //                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }

                    Request authorisedRequest = originalRequest.newBuilder()
                            .header("Authorization", mToken)
                            .header("Connection", "Keep-Alive") //新添加，time-out默认是多少呢？
                            .build();

                    Response originalResponse = chain.proceed(authorisedRequest);
                    return originalResponse.newBuilder()
                            //                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();


                }
            };

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(11, TimeUnit.SECONDS)
                    //                    .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                    .addNetworkInterceptor(mRequestInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .authenticator(mAuthenticator)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();


            apiService = retrofit.create(ApiService.class);
        }

        return apiService;
    }

    private static int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }

    /**
     * check if already has auth header
     *
     * @param originalRequest
     */
    private static boolean alreadyHasAuthorizationHeader(Request originalRequest) {
        if (originalRequest.headers().toString().contains("Authorization")) {
            Log.w(TAG, "already add Auth header");
            return true;
        }
        return false;
    }

    /**
     * some request after login/oauth before logout
     * but they no need oauth,so do not add auth header
     *
     * @param originalRequest
     */
    private static boolean noNeedAuth(Request originalRequest) {
        if (originalRequest.headers().toString().contains("NoNeedAuthFlag")) {
            Log.d("WW", "no need auth !");
            return true;
        }
        return false;
    }

    /**
     * uese refresh token to Refresh an Access Token
     *
     * 使用同步的方式请求，不是异步
     *
     *
     */
    private static void refreshToken() {
//        if (TextUtils.isEmpty(SharedPreferencesDao.getInstance().getData(SPKey.KEY_REFRESH_TOKEN, "", String.class))) {
//            return;
//        }
//        LoginParams loginParams = new LoginParams();
//        loginParams.setClient_id("5e96eac06151d0ce2dd9554d7ee167ce");
//        loginParams.setClient_secret("aCE34n89Y277n3829S7PcMN8qANF8Fh");
//        loginParams.setGrant_type("refresh_token");
//        loginParams.setRefresh_token(SharedPreferencesDao.getInstance().getData(SPKey.KEY_REFRESH_TOKEN, "", String.class));

//        Call<HttpResponse<LoginResult>> refreshTokenCall = HttpCall.getApiService().refreshToken(loginParams);
//        try {
//            retrofit2.Response<HttpResponse<LoginResult>> response = refreshTokenCall.execute();
//            if (response.isSuccessful()) {
//                int responseCode = response.body().getCode();
//                if (responseCode == 0) {
//                    HttpResponse<LoginResult> httpResponse = response.body();
//                    SharedPreferencesDao.getInstance().saveData(SPKey.KEY_ACCESS_TOKEN, "Bearer " + httpResponse.getResult().getAccessToken());
//                    SharedPreferencesDao.getInstance().saveData(SPKey.KEY_REFRESH_TOKEN, httpResponse.getResult().getRefreshToken());
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }



}
