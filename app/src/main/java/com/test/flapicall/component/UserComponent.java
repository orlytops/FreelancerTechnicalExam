package com.test.flapicall.component;

import com.test.flapicall.activities.MainActivity;
import com.test.flapicall.api.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by orly on 9/6/17.
 */
@Singleton
@Component(modules = {ApiModule.class})
public interface UserComponent {

  void inject(MainActivity mainActivity);

}
