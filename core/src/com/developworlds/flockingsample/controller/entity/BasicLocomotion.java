package com.developworlds.flockingsample.controller.entity;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.utility.LowPassFilter;
import com.developworlds.flockingsample.world.entity.Boid;

/**
 * Created by benjamin-sergent on 5/30/15.
 */
public class BasicLocomotion extends Locomotion {
    private Vector2 toGoal = new Vector2();
    LowPassFilter facingFilter = new LowPassFilter(0.1f);

    @Override
    public void update(Boid boid, float deltaTime) {
        toGoal.set(boid.goal);
        toGoal.sub(boid.position);

        if(toGoal.len2() < boid.size.x*boid.size.x) {
        // The goal is inside the boid
            return;
        }

        boid.currSpeed = Math.min(boid.currSpeed, boid.maxSpeed);
        toGoal.nor().scl(boid.currSpeed * deltaTime);


        boid.position.set(boid.position.x + toGoal.x, boid.position.y + toGoal.y);

        facingFilter.addValue(toGoal.angle() - 90);
        boid.setFacingRotation(facingFilter.get());
    }
}
