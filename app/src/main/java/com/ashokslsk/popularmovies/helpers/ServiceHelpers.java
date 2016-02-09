package com.ashokslsk.popularmovies.helpers;

import android.content.Context;

import com.ashokslsk.popularmovies.R;
import com.ashokslsk.popularmovies.network.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by ashok.kumar on 09/02/16.
 */
public class ServiceHelpers {

    public ServiceHelpers() {
    }


    public static <S> S createService(Class<S> serviceClass, final Context context) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Constants.MOVIE_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(InitOkHttpClient(context));

        return builder.build().create(serviceClass);
    }


    private static OkHttpClient InitOkHttpClient(final Context context) {
        int connTimeout = context.getResources().getInteger(R.integer.http_timeout);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(connTimeout, TimeUnit.SECONDS)
                .readTimeout(connTimeout, TimeUnit.SECONDS)
                .writeTimeout(connTimeout, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request original = chain.request();

                                // Request customization: add request headers
                                Request.Builder requestBuilder = original.newBuilder()
                                        .header("Accept", "application/json")
                                        .method(original.method(), original.body());

                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            }
                        });
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);
        OkHttpClient client = builder.build();

        return client;
    }
}
