package com.developworlds.flockingsample.utility;

/**
 * Created by benjamin-sergent on 5/30/15.
 */
public class LowPassFilter {
    private float passConstant;
    private float currVal = 0;

    public LowPassFilter(float passConstant) {
        this.passConstant = passConstant;
    }

    public void addValue(float val) {
        currVal = passConstant * val + (1 - passConstant) * currVal;
    }

    public float get() {
        return currVal;
    }
}
