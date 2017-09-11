package com.test.flapicall.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.test.flapicall.R;
import com.test.flapicall.api.models.User;
import com.test.flapicall.api.services.UserService;
import com.test.flapicall.fragment.ProfileFragment;
import com.test.flapicall.fragment.ReviewFragment;
import com.test.flapicall.presenters.UserPresenter;

import javax.inject.Inject;

import rx.Observer;

public class MainActivity extends BaseActivity {

  private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;

  @Inject
  UserService userService;

  private Gson _gson;

  private User   _user;
  private String _userJson;

  private RoundedImageView _profileImage;
  private ViewPager        _profileViewPager;
  private NestedScrollView _profileScroll;
  private AppBarLayout     _appBarLayout;
  private TextView         _nameText;
  private TextView         _nameSubText;
  private TextView         _profileText;
  private TextView         _reviewText;
  private RelativeLayout   _saveProfileLayout;
  private Toolbar          _profileToolbar;

  private ProfilePagerAdapter _profilePagerAdapter;

  private int _maxScrollSize;
  private boolean _isAvatarShown = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getUserComponent().inject(this);
    _gson = new Gson();

    initViews();
    initListeners();
    populateViews();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  private void initViews() {
    _profileImage = (RoundedImageView) findViewById(R.id.image_profile);
    _profileViewPager = (ViewPager) findViewById(R.id.viewpager_profile);
    _profileScroll = (NestedScrollView) findViewById(R.id.scroll_profile);
    _appBarLayout = (AppBarLayout) findViewById(R.id.appbar_profile);
    _nameText = (TextView) findViewById(R.id.text_name);
    _nameSubText = (TextView) findViewById(R.id.text_name_sub);
    _profileText = (TextView) findViewById(R.id.text_profile);
    _reviewText = (TextView) findViewById(R.id.text_review);
    _saveProfileLayout = (RelativeLayout) findViewById(R.id.layout_save_profile);
    _profileToolbar = (Toolbar) findViewById(R.id.toolbar_profile);
    _profileToolbar.setTitle("");
    setSupportActionBar(_profileToolbar);

    _profileScroll.setFillViewport(true);
    _maxScrollSize = _appBarLayout.getTotalScrollRange();
  }


  private void initListeners() {
    _appBarLayout.addOnOffsetChangedListener(onOffsetChangedListener);
    _profileViewPager.setOnPageChangeListener(onPageChangeListener);
    _profileText.setOnClickListener(onClickListener);
    _reviewText.setOnClickListener(onClickListener);
  }


  private void initAdapter() {
    _profilePagerAdapter = new ProfilePagerAdapter(getSupportFragmentManager());
    _profilePagerAdapter.setUserJson(_userJson);
    _profileViewPager.setAdapter(_profilePagerAdapter);
  }

  private void populateViews() {
    UserPresenter _userPresenter = new UserPresenter(userService);
    //Change .getUser if you're using freelancer.com API
    _userPresenter.getUserMock(userObserver);
  }

  private void setProfileActive() {
    _profileText.setTextColor(getResources().getColor(R.color.white));
    _profileText.setBackgroundColor(getResources().getColor(R.color.fl_blue));

    _reviewText.setTextColor(getResources().getColor(R.color.fl_blue));
    _reviewText.setBackgroundColor(getResources().getColor(R.color.white));
    _saveProfileLayout.setVisibility(View.VISIBLE);
  }

  private void setReviewActive() {
    _reviewText.setTextColor(getResources().getColor(R.color.white));
    _reviewText.setBackgroundColor(getResources().getColor(R.color.fl_blue));

    _profileText.setTextColor(getResources().getColor(R.color.fl_blue));
    _profileText.setBackgroundColor(getResources().getColor(R.color.white));
    _saveProfileLayout.setVisibility(View.GONE);
  }

  private Observer<User> userObserver = new Observer<User>() {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
      Log.e("Error: ", e.getMessage());
    }

    @Override
    public void onNext(User user) {
      _userJson = _gson.toJson(user);
      _nameText.setText(user.getUserName());
      _nameSubText.setText(user.getUserName());
      Glide.with(getApplicationContext()).load(user.getAvatar()).placeholder(
          R.drawable.ic_mystery_man).into(_profileImage);
      initAdapter();
    }
  };

  private View.OnClickListener onClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      switch (view.getId()) {
      case R.id.text_profile:
        setProfileActive();
        _profileViewPager.setCurrentItem(0, true);
        break;
      case R.id.text_review:
        setReviewActive();
        _profileViewPager.setCurrentItem(1, true);
        break;
      }
    }
  };

  private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager
      .OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

      switch (position) {
      case 0:
        setProfileActive();
        break;
      case 1:
        setReviewActive();
        break;
      }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
  };

  private AppBarLayout.OnOffsetChangedListener onOffsetChangedListener =
      new AppBarLayout.OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
          if (_maxScrollSize == 0) {
            _maxScrollSize = appBarLayout.getTotalScrollRange();
          }

          int percentage = (Math.abs(verticalOffset)) * 40 / _maxScrollSize;
          if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && _isAvatarShown) {
            _isAvatarShown = false;
            _profileImage.animate()
                .scaleY(0).scaleX(0)
                .setDuration(100)
                .start();
          }

          if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !_isAvatarShown) {
            _isAvatarShown = true;

            _profileImage.animate()
                .scaleY(1).scaleX(1)
                .start();
          }
        }
      };

  public static class ProfilePagerAdapter extends FragmentPagerAdapter {

    private String userJson;

    public ProfilePagerAdapter(FragmentManager fragmentManager) {
      super(fragmentManager);
    }

    @Override
    public int getCount() {
      return 2;
    }

    @Override
    public Fragment getItem(int position) {
      switch (position) {
      case 0:
        return new ProfileFragment().newInstance(userJson);
      case 1:
        return new ReviewFragment();
      default:
        return null;
      }
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return "Page " + position;
    }

    public void setUserJson(String userJson) {
      this.userJson = userJson;
    }
  }

}
