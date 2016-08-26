package com.example.jinliangshan.littlezhihu.home.util;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by jinliangshan on 16/8/25.
 */
public class FragmentManagerUtil {
    private FragmentManagerUtil(){}

    public static void addFragment(FragmentManager fm, @IdRes int idFragmentContainer, Fragment fragment) {
        fm.beginTransaction()
                .add(idFragmentContainer, fragment)
                .commit();
    }
}
