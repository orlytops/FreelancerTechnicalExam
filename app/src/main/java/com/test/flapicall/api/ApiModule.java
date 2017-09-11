package com.test.flapicall.api;

import com.test.flapicall.BuildConfig;
import com.test.flapicall.api.services.UserService;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by orly on 9/6/17.
 */

@Module
public class ApiModule {

  public ApiModule() {

  }

  @Provides
  @Singleton
  Retrofit providesRetrofit() {

    //use BuildConfig.BASEURL for freelancer API
    //use BuildConfig.MOCKURL for freelancer API
    return new Retrofit.Builder()
        .baseUrl(BuildConfig.MOCKURL)
        .client(getClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
  }

  private OkHttpClient getClient() {
    OkHttpClient.Builder client = new OkHttpClient.Builder();
    client.addInterceptor(new Interceptor() {
      @Override
      public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
            .header("Content-Type", "application/json")
            .method(original.method(), original.body())
            .build();

        return chain.proceed(request);
      }
    });
    client.interceptors().add(
        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));


    return client.build();
  }

  @Provides
  @Singleton
  public UserService providesUserService(Retrofit retrofit) {
    return retrofit.create(UserService.class);
  }

}
