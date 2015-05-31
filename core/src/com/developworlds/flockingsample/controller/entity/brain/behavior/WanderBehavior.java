package com.developworlds.flockingsample.controller.entity.brain.behavior;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.entity.Boid;

public class WanderBehavior {
    private float circleDistance = 500;
    private float innerCircleRadius = 450;
    private float outerCircleRadius = 30;
    private float maxJitterPerSec = (float) (2 * Math.PI);
    private float currInnerAngle;

    private Vector2 target = new Vector2();
    private Vector2 innerPoint = new Vector2();
    private Vector2 outerPoint = new Vector2();

    public Vector2 getWanderTarget(Boid boid, float deltaTime) {
        float maxJitter = maxJitterPerSec * deltaTime;

        target.set(boid.velocity).nor().scl(circleDistance);
        target.add(boid.position);

        currInnerAngle += getJitter(maxJitter);
        float currOuterAngle = getRandomAngle();

        innerPoint.set((float) Math.cos(currInnerAngle), (float) Math.sin(currInnerAngle)).scl(innerCircleRadius);
        outerPoint.set((float) Math.cos(currOuterAngle), (float) Math.sin(currOuterAngle)).scl(outerCircleRadius);

        target.add(innerPoint).add(outerPoint);

        return target; // We reset this at the beginning, so we don't bother to make a copy.
    }

    private float getRandomAngle() {
        return (float) (Math.random() * 4*Math.PI - 2*Math.PI);
    }

    private float getJitter(float maxJitter) {
        // in the range [-maxJitter,maxJitter]
        return (float) ((Math.random() - 0.5) * 2 * maxJitter);
    }

    public float getInnerCircleRadius() {
        return innerCircleRadius;
    }

    public void setInnerCircleRadius(float innerCircleRadius) {
        this.innerCircleRadius = innerCircleRadius;
    }

    public float getOuterCircleRadius() {
        return outerCircleRadius;
    }

    public void setOuterCircleRadius(float outerCircleRadius) {
        this.outerCircleRadius = outerCircleRadius;
    }

    public float getMaxJitterPerSec() {
        return maxJitterPerSec;
    }

    public void setMaxJitterPerSec(float maxJitterPerSec) {
        this.maxJitterPerSec = maxJitterPerSec;
    }
}
