package com.developworlds.flockingsample.controller.entity.locomotion;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.entity.Boid;

public class WrappingLocomotion extends Locomotion {
    private Rectangle bounds;
    private Vector2 acceleration = new Vector2();

    public WrappingLocomotion(Rectangle bounds) {
        this.bounds = bounds;
    }

    // The simplest locomotion attempts to match the desired velocity
    // without worrying about interpenetration or other movement rules.
    @Override
    public void update(Boid boid, float deltaTime) {
        acceleration.set(boid.desiredVelocity);
        acceleration.sub(boid.velocity);

        Locomotion.integrate(boid, acceleration.nor().scl(boid.maxAcceleration), deltaTime);

        wrapBoidPosition(boid);
    }

    private void wrapBoidPosition(Boid boid) {
        float halfWidth = boid.size.x/2;
        float halfHeight = boid.size.y/2;
        if(boid.position.x + halfWidth < bounds.x) {
            boid.position.x += bounds.width;
        }

        if(boid.position.y + halfHeight < bounds.y) {
            boid.position.y += bounds.height;
        }

        if(boid.position.x - halfWidth > bounds.width) {
            boid.position.x -= bounds.width;
        }

        if(boid.position.y - halfHeight  > bounds.height) {
            boid.position.y -= bounds.height;
        }
    }
}
