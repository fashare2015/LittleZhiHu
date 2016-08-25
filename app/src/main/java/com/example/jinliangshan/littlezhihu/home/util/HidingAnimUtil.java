package com.example.jinliangshan.littlezhihu.home.util;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.View;

/**
 * Created by jinliangshan on 16/8/25.
 */
public class HidingAnimUtil implements HidingAnim {
    public static final int HIDING_MOD_TOP = 0;
    public static final int HIDING_MOD_BOTTOM = 1;

    private AnimatorSet mAnimator = new AnimatorSet();
    private long mAnimationDuration = 200;
    private View mView;

    private int mHidingMod = -1;
    private float mHideDy = 0, mShowDy = 0;

    private boolean isShow = true;

    public HidingAnimUtil setHidingMod(int mHidingMod) {
        this.mHidingMod = mHidingMod;
//        initDy();
        return this;
    }

    public HidingAnimUtil(View mView) {
        this.mView = mView;
        initAnim();
    }

    private void initAnim() {
        mAnimator.setDuration(mAnimationDuration)
                .setInterpolator(new FastOutSlowInInterpolator());
    }

    @Override
    public void show() {
        startAnim(true);
    }

    @Override
    public void hide() {
        startAnim(false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startAnim(boolean toShow){
//        Log.d("HidingAnimUtil", toShow? "show": "hide");
        if(mView == null || mAnimator.isRunning()
                || mHidingMod == -1 || toShow == isShow)
            return ;
        Log.d("HidingAnimUtil", toShow? "show": "hide");
        mAnimator.play(ObjectAnimator.ofFloat(mView, mView.TRANSLATION_Y,
                mView.getTranslationY(), getDy(toShow)));
        mAnimator.start();

        isShow = !isShow;
    }

    /**
     * (show 状态的)初始位置 相对与 目标位置 的偏移量
     * @param toShow
     * @return
     */
    private float getDy(boolean toShow) {
        initDy();
        return toShow? mShowDy: mHideDy;
    }

    private void initDy(){
        if(mHideDy != 0)
            return ;
        if(mHidingMod == HIDING_MOD_TOP){
            mHideDy = -mView.getBottom();
        }else{
            mHideDy = mView.getRootView().getHeight() - mView.getTop();
        }
        mShowDy = 0;
    }

}
