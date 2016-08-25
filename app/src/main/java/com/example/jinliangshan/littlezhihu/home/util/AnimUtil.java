package com.example.jinliangshan.littlezhihu.home.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by jinliangshan on 16/8/25.
 */
public class AnimUtil implements HidingAnim {

    private AnimatorSet mAnimator = new AnimatorSet();
    private long mAnimationDuration = 200;


    @Override
    public void show(View view) {
    }

    @Override
    public void hide(View view) {
        startAnim(view, false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startAnim(View view, boolean toShow){
        Log.e("AnimUtil", toShow? "show": "hide");
        if(view == null){
            Log.e("AnimUtil", "null");
            return ;
        }

        View mAppbarView = ((LinearLayout)view).getChildAt(0);

        mAnimator = new AnimatorSet().setDuration(mAnimationDuration);
        mAnimator.setInterpolator(new FastOutSlowInInterpolator());
        mAnimator.play(ObjectAnimator.ofFloat(view, view.TRANSLATION_Y, view.getTranslationY(), -view.getBottom()))
                .before(ObjectAnimator.ofFloat(mAppbarView, view.TRANSLATION_Z, mAppbarView.getTranslationZ(), -mAppbarView.getElevation()));

        mAnimator.start();

    }

}
