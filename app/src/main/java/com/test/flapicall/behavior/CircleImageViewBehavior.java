package com.test.flapicall.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.makeramen.roundedimageview.RoundedImageView;

public class CircleImageViewBehavior extends CoordinatorLayout.Behavior<RoundedImageView> {

  private int _startHeight;

  public CircleImageViewBehavior() {

  }

  public CircleImageViewBehavior(Context context, AttributeSet attrs) {
    super();
  }


  @Override
  public boolean layoutDependsOn(CoordinatorLayout parent, RoundedImageView child,
      View dependency) {
    return dependency instanceof Toolbar;
  }

  @Override
  public boolean onDependentViewChanged(CoordinatorLayout parent, RoundedImageView child,
      View dependency) {
    initProperties(child, dependency);

    int _changeBehaviorPoint = 1;

    final int maxScrollDistance = (int) (60);
    float expandedPercentageFactor = dependency.getY() / maxScrollDistance;

    if (expandedPercentageFactor < _changeBehaviorPoint) {
      float heightFactor = (_changeBehaviorPoint - expandedPercentageFactor) / _changeBehaviorPoint;
      float heightToSubtract = ((_startHeight - 300) * heightFactor);
      CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
      lp.width = (int) (_startHeight - heightToSubtract);
      lp.height = (int) (_startHeight - heightToSubtract);
      child.setLayoutParams(lp);
    } else {
      CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
      lp.width = (int) (_startHeight);
      lp.height = (int) (_startHeight);
      child.setLayoutParams(lp);
    }
    return true;
  }

  private void initProperties(RoundedImageView child, View dependency) {
    if (_startHeight == 0) { _startHeight = child.getHeight(); }
  }

}