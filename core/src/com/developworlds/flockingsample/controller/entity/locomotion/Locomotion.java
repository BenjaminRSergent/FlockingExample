package com.developworlds.flockingsample.controller.entity.locomotion;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public abstract class Locomotion {
    // Slower than 2 px per second
    private final static float MIN_VELO_SQ = 2 * 2;

    public abstract void update(Boid boid, World world, float deltaTime);

    private static Vector2 motion = new Vector2();

    public static void integrate(Boid boid, Vector2 acceleration, float deltaTime) {
        boid.velocity.add(acceleration.scl(deltaTime));
        if (boid.velocity.len2() > boid.maxSpeed * boid.maxSpeed) {
            boid.velocity.nor().scl(boid.maxSpeed);
        }

        if (boid.velocity.len2() < MIN_VELO_SQ) {
            // This is to avoid twitching when attempting to stop at a goal.
            boid.velocity.set(0, 0);
        }

        motion.set(boid.velocity);
        motion.scl(deltaTime);

        boid.position.add(motion);
    }
}
