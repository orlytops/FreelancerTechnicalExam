package com.test.flapicall.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.test.flapicall.component.DaggerUserComponent;
import com.test.flapicall.component.UserComponent;

/**
 * Created by orly on 9/6/17.
 */

public class BaseActivity extends AppCompatActivity {

  private UserComponent userComponent;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    userComponent = DaggerUserComponent.builder().build();
  }

  public UserComponent getUserComponent() {
    return userComponent;
  }
}
