package com.test.flapicall.api.services;

import com.test.flapicall.api.models.User;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by orly on 9/6/17.
 */

public interface UserService {

  //form freelancer.com API
  @GET("api/danapotplant")
  Observable<User> getUser();

  //mock API
  @GET("v2/59b352851200005c0d892540")
  Observable<User> getUserMock();

}
