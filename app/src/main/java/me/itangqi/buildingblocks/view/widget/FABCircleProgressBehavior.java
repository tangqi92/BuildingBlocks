package me.itangqi.buildingblocks.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.github.jorgecastilloprz.FABProgressCircle;

/**
 * 方法来自 [issue10](https://github.com/JorgeCastilloPrz/FABProgressCircle/issues/10)
 * Created by Troy on 2015/10/4.
 */
public class FABCircleProgressBehavior extends CoordinatorLayout.Behavior {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean mIsAnimatingOut = false;

    public FABCircleProgressBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0 && !this.mIsAnimatingOut && child.getVisibility() == View.VISIBLE) {
            animateOut(child);
        }
//        else if (dyConsumed < CommonUtils.getToolbarHeight(App.getContext()) && child.getVisibility() != View.VISIBLE) {
//            animateIn(child);
//        }
    }

    // Same animation that FloatingActionButton.Behavior uses to hide the FAB when the AppBarLayout exits
    private void animateOut(final View button) {
        if (((FABProgressCircle) button).getVisibility() == View.VISIBLE) {
            if (Build.VERSION.SDK_INT >= 14) {
                ViewCompat.animate(button).scaleX(0.0F).scaleY(0.0F).alpha(0.0F).setInterpolator(INTERPOLATOR).withLayer()
                        .setListener(new ViewPropertyAnimatorListener() {
                            public void onAnimationStart(View view) {
                                FABCircleProgressBehavior.this.mIsAnimatingOut = true;
                            }

                            public void onAnimationCancel(View view) {
                                FABCircleProgressBehavior.this.mIsAnimatingOut = false;
                            }

                            public void onAnimationEnd(View view) {
                                FABCircleProgressBehavior.this.mIsAnimatingOut = false;
                                view.setVisibility(View.GONE);
                            }
                        }).start();
            } else {
                Animation anim = AnimationUtils.loadAnimation(button.getContext(), android.R.anim.fade_out);
                anim.setInterpolator(INTERPOLATOR);
                anim.setDuration(200L);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                        FABCircleProgressBehavior.this.mIsAnimatingOut = true;
                    }

                    public void onAnimationEnd(Animation animation) {
                        FABCircleProgressBehavior.this.mIsAnimatingOut = false;
                        button.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(final Animation animation) {
                    }
                });
                button.startAnimation(anim);
            }
        }
    }

    // Same animation that FloatingActionButton.Behavior uses to show the FAB when the AppBarLayout enters
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void animateIn(View button) {
        if (((FABProgressCircle) button).getVisibility() == View.INVISIBLE) {
            if (Build.VERSION.SDK_INT >= 14) {
                ViewCompat.animate(button).scaleX(1.0F).scaleY(1.0F).alpha(1.0F)
                        .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
                        .start();
            } else {
                Animation anim = AnimationUtils.loadAnimation(button.getContext(), android.R.anim.fade_in);
                anim.setDuration(200L);
                anim.setInterpolator(INTERPOLATOR);
                button.startAnimation(anim);
            }
        }
    }
}
