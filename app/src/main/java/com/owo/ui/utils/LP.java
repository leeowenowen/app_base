package com.owo.ui.utils;

import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class LP {
    public static final FrameLayout.LayoutParams FMM = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
    public static final FrameLayout.LayoutParams FWWL = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.LEFT);
    public static final FrameLayout.LayoutParams FWWC = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.CENTER);
    public static final FrameLayout.LayoutParams FWWR = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.RIGHT);
    public static final FrameLayout.LayoutParams FWWRB = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.RIGHT | Gravity.BOTTOM);
    public static final FrameLayout.LayoutParams FWWRCV = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.RIGHT | Gravity.CENTER_VERTICAL);
    public static final FrameLayout.LayoutParams FWWTR = new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.TOP | Gravity.RIGHT);

    public static final FrameLayout.LayoutParams FWWBCH() {
        return new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
    }

    public static final LinearLayout.LayoutParams LMW() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static final FrameLayout.LayoutParams FMM() {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
    }

    public static final LinearLayout.LayoutParams LWM = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

    /*
     * There exist some bug(BasePage scroll view)
     */
    public static final LinearLayout.LayoutParams LMM() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
    }

    public static final LinearLayout.LayoutParams LWW() {
        return new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static final LinearLayout.LayoutParams L0W1 = new LinearLayout.LayoutParams(0,
            LinearLayout.LayoutParams.WRAP_CONTENT, 1);
    public static final LinearLayout.LayoutParams L0M1 = new LinearLayout.LayoutParams(0,
            LinearLayout.LayoutParams.MATCH_PARENT, 1);
    public static final LinearLayout.LayoutParams LM01 = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);
    public static final LinearLayout.LayoutParams LW01 = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1);

    public static LinearLayout.LayoutParams lp(int width, int height) {
        return new LinearLayout.LayoutParams(width, height);
    }

    public static LinearLayout.LayoutParams lp(int width, int height, int weight) {
        return new LinearLayout.LayoutParams(width, height, weight);
    }

    public static FrameLayout.LayoutParams fp(int width, int height) {
        return new FrameLayout.LayoutParams(width, height);
    }

    public static FrameLayout.LayoutParams fp(int width, int height, int gravity) {
        return new FrameLayout.LayoutParams(width, height, gravity);
    }

    public static FrameLayout.LayoutParams fpWW(int gravity) {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT, gravity);
    }

    public static FrameLayout.LayoutParams fpWM(int gravity) {
        return new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.MATCH_PARENT, gravity);
    }
}
