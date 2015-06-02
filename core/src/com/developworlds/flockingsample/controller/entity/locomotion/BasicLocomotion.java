package com.developworlds.flockingsample.controller.entity.locomotion;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class BasicLocomotion extends Locomotion {
    private Vector2 acceleration = new Vector2();

    // The simplest locomotion attempts to match the desired velocity
    // without worrying about interpenetration or other movement rules.
    @Override
    public void update(Boid boid, World world, float deltaTime) {
        acceleration.set(boid.desiredVelocity);
        acceleration.sub(boid.velocity);

        Locomotion.integrate(boid, acceleration.nor().scl(boid.maxAcceleration), deltaTime);
    }
}
