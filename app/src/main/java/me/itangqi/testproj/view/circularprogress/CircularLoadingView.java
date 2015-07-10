package me.itangqi.testproj.view.circularprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import me.itangqi.testproj.R;


/**
 * Simplest custom view possible, using CircularProgressDrawable
 */
public class CircularLoadingView extends View {

    private CircularProgressDrawable mDrawable;

    public CircularLoadingView(Context context) {
        this(context, null);
    }

    public CircularLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDrawable = new CircularProgressDrawable(getContext().getResources().getColor(R.color.primary), 10);
        mDrawable.setCallback(this);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            mDrawable.start();
        } else {
            mDrawable.stop();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDrawable.setBounds(0, 0, w, h);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mDrawable.draw(canvas);
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mDrawable || super.verifyDrawable(who);
    }
}
