package com.developworlds.flockingsample.controller.entity.locomotion;

import com.badlogic.gdx.math.Rectangle;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class TeleportLocomotion extends Locomotion {
    private Rectangle bounds;

    public TeleportLocomotion(Rectangle bounds) {
        this.bounds = bounds;
    }

    // The simplest locomotion attempts to match the desired velocity
    // without worrying about interpenetration or other movement rules.
    @Override
    public void update(Boid boid, World world, float deltaTime) {
        Locomotion.integrate(boid, deltaTime);

        teleportOnEdge(boid);
    }

    private void teleportOnEdge(Boid boid) {
        float halfWidth = boid.size.x / 2;
        float halfHeight = boid.size.y / 2;
        boolean doTeleport = boid.position.x + halfWidth < bounds.x || boid.position.y + halfHeight < bounds.y || boid.position.x - halfWidth > bounds.width || boid.position.y - halfHeight > bounds.height;

        if (doTeleport) {
            boid.position.set(bounds.getWidth() * (float) Math.random(), bounds.getHeight() * (float) Math.random());
            boid.velocity.set(0, 0);
        }
    }
}
