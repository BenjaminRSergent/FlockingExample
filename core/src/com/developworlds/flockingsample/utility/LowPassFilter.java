package com.developworlds.flockingsample.utility;

import com.badlogic.gdx.math.Vector2;

public class LowPassFilter {
    private float passConstant;
    private Vector2 currVal = new Vector2();
    private Vector2 tmpVal = new Vector2();

    public LowPassFilter(float passConstant) {
        this.passConstant = passConstant;
    }

    public void addValue(Vector2 val) {
        tmpVal.set(val);
        currVal.set(tmpVal.scl(passConstant).add(currVal.scl(1 - passConstant)));
    }

    public Vector2 get() {
        return currVal;
    }
}
