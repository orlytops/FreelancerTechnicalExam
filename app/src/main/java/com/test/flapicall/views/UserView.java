package com.test.flapicall.views;

import com.test.flapicall.api.models.User;

/**
 * Created by orly on 9/6/17.
 */

public interface UserView {

  void showProgress();

  void hideProgress();

  void onSuccess(User user);

  void onError(String errorMessage);
}
