package com.developworlds.flockingsample.controller.entity.behavior.goals;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.controller.entity.behavior.steering.SteeringMethods;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class WanderBehavior implements Behavior {
    private float circleDistance = 100;
    private float circleRadius = 90;
    private float maxJitterPerSec = (float) (4 * Math.PI);
    private float currAngle;
    Vector2 target = new Vector2();
    Vector2 innerPoint = new Vector2();

    public WanderBehavior() {
        currAngle = getRandomAngle();
    }



    public Vector2 getSteeringForce(Boid boid, World world, float deltaTime, Vector2 force) {
        float maxJitter = maxJitterPerSec * deltaTime;

        target.set(boid.velocity).nor().scl(circleDistance);
        target.add(boid.position);

        currAngle += getJitter(maxJitter);

        innerPoint.set((float) Math.cos(currAngle), (float) Math.sin(currAngle)).scl(circleRadius + boid.getRadius());
        target.add(innerPoint);


        SteeringMethods.seek(boid, target, force);

        return force;
    }

    private float getRandomAngle() {
        return (float) (Math.random() * 2 * Math.PI);
    }

    private float getJitter(float maxJitter) {
        // in the range [-maxJitter,maxJitter]
        return (float) ((Math.random() - 0.5) * 2 * maxJitter);
    }

    public float getCurrAngle() {
        return currAngle;
    }

    public void setCurrAngle(float currAngle) {
        this.currAngle = currAngle;
    }

    public float getCircleDistance() {
        return circleDistance;
    }

    public void setCircleDistance(float circleDistance) {
        this.circleDistance = circleDistance;
    }

    public float getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
    }

    public float getMaxJitterPerSec() {
        return maxJitterPerSec;
    }

    public void setMaxJitterPerSec(float maxJitterPerSec) {
        this.maxJitterPerSec = maxJitterPerSec;
    }


}
