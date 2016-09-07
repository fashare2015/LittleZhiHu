package com.example.jinliangshan.littlezhihu.home.widget;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.Log;

import com.example.jinliangshan.littlezhihu.home.base.BasePagerAdapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时适配器
 * @param <T>
 */
public abstract class TimerPagerAdapter<T> extends BasePagerAdapter<T> implements Timer{
    private static final String TAG = "TimerPagerAdapter";

    @IntDef({RESUME, PAUSE, DESTROY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LifeCycle {
    }

    public static final int RESUME = 0;
    public static final int PAUSE = 1;
    public static final int DESTROY = 2;
    /**
     * 生命周期状态，保证{@link #mTimer}在各生命周期选择执行策略
     */
    private int mLifeCycle = RESUME;
    /**
     * 是否正在触摸状态，用以防止触摸滑动和自动轮播冲突
     */
    private boolean mIsTouching = false;
    /**
     * 轮播定时器
     */
    private ScheduledExecutorService mTimer;

    private OnTimerSchedule mOnTimerSchedule;

    public void setOnTimerSchedule(OnTimerSchedule onTimerSchedule) {
        mOnTimerSchedule = onTimerSchedule;
    }

    public TimerPagerAdapter(Context context) {
        super(context);
    }

    public void setLifeCycle(@LifeCycle int lifeCycle) {
        this.mLifeCycle = lifeCycle;
    }

    public void setTouching(boolean touching) {
        mIsTouching = touching;
    }

    @Override
    public void start() {
        Log.i(TAG, "start");
        shutdownTimer();
        mTimer = Executors.newSingleThreadScheduledExecutor();

        mTimer.scheduleAtFixedRate(() -> {
            switch (mLifeCycle) {
                case RESUME:
                    if (!mIsTouching && getCount() > 1)
                        if(mOnTimerSchedule != null) {
                            mOnTimerSchedule.run();
                        }
                    break;
                case PAUSE:
                    break;
                case DESTROY:
                    shutdownTimer();
                    break;
            }
        }, 1000 * 2, 1000 * 2, TimeUnit.MILLISECONDS);
    }

    @Override
    public void pause() {
        shutdownTimer();
    }

    @Override
    public void stop(){
        Log.i(TAG, "stop");
        shutdownTimer();
    }

    private void shutdownTimer() {
        if (mTimer != null && !mTimer.isShutdown()) {
            mTimer.shutdown();
        }
        mTimer = null;
    }

    public interface OnTimerSchedule{
        void run();
    }
}