package com.ytjojo.commonlib.anim;
import android.view.animation.Interpolator;

public class DecelerateAccelerateInterpolator implements Interpolator {
    private float mFactor = 1.0f;

    public DecelerateAccelerateInterpolator() {
    }

    public DecelerateAccelerateInterpolator(float factor) {
        mFactor = factor;
    }

    public float getInterpolation(float input) {
        float result;
        if (input < 0.5) {
            result = (float) (1.0f - Math.pow((1.0f - 2 * input), 2 * mFactor)) / 2;
        } else {
            result = (float) Math.pow((input - 0.5) * 2, 2 * mFactor) / 2 + 0.5f;
        }
        return result;
    }
}