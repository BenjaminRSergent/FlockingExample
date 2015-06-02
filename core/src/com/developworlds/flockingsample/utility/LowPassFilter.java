package com.developworlds.flockingsample.utility;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.FlockingApplication;

public class LowPassFilter {
    private float passConstant;
    private Vector2 currVal = new Vector2();

    public LowPassFilter(float passConstant) {
        this.passConstant = passConstant;
    }

    public void addValue(Vector2 val) {
        Vector2 tmpVal = FlockingApplication.vectorPool.obtain();
        tmpVal.set(val);
        currVal.set(tmpVal.scl(passConstant).add(currVal.scl(1 - passConstant)));
        FlockingApplication.vectorPool.free(tmpVal);
    }

    public Vector2 get() {
        return currVal;
    }
}
