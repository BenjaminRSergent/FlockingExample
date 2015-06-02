package com.developworlds.flockingsample.controller.entity.locomotion;

import com.badlogic.gdx.math.Rectangle;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class WrappingLocomotion extends Locomotion {
    private Rectangle bounds;

    public WrappingLocomotion(Rectangle bounds) {
        this.bounds = bounds;
    }

    // The simplest locomotion attempts to match the desired velocity
    // without worrying about interpenetration or other movement rules.
    @Override
    public void update(Boid boid, World world, float deltaTime) {
        Locomotion.integrate(boid, deltaTime);

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
