package com.test.flapicall.presenters;

import com.test.flapicall.api.models.User;
import com.test.flapicall.api.services.UserService;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by orly on 9/6/17.
 */

public class UserPresenter {

  private final UserService userService;

  public UserPresenter(UserService userService) {
    this.userService = userService;
  }

  public void getUser(Observer<User> userObserver) {
    userService.getUser()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(userObserver);
  }

  public void getUserMock(Observer<User> userObserver) {
    userService.getUserMock()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(userObserver);
  }


}
