package com.developworlds.flockingsample.utility;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by benjamin-sergent on 5/30/15.
 */
public class LowPassFilter {
    private float passConstant;
    private Vector2 currVal = new Vector2();

    public LowPassFilter(float passConstant) {
        this.passConstant = passConstant;
    }

    public void addValue(Vector2 val) {
        currVal = val.cpy().scl(passConstant).add(currVal.scl(1 - passConstant));
    }

    public Vector2 get() {
        return currVal;
    }
}
