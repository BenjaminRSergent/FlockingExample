package com.developworlds.flockingsample.controller.entity.brain;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.entity.Boid;

/**
 * Created by benjamin-sergent on 5/30/15.
 */
public class Behaviors {
    public static Vector2 arrive(Boid boid, Vector2 goal, float slowdownRadius) {
        float closeEnough = Math.max(boid.size.x, boid.size.y);

        Vector2 desiredVelocity = goal.cpy();
        desiredVelocity.sub(boid.position);

        float dist = desiredVelocity.len();

        if (dist < closeEnough) {
            // Desire to stop
            desiredVelocity.set(0, 0);
            return desiredVelocity;
        }

        float desiredSpeed = boid.maxSpeed;
        if (dist < slowdownRadius) {
            desiredSpeed = boid.maxSpeed * ((slowdownRadius - dist) / slowdownRadius);
        }


        // Desire to go in the direction of the goal as fast as possible

        if (dist > 0) {
            desiredVelocity.scl(1 / dist).scl(desiredSpeed);
        }

        return desiredVelocity;
    }

    public static Vector2 seek(Boid boid, Vector2 goal) {
        float closeEnough = Math.max(boid.size.x, boid.size.y);

        Vector2 desiredVelocity = new Vector2();
        desiredVelocity.set(goal);
        desiredVelocity.sub(boid.position);

        float dist = desiredVelocity.len();

        if (dist < closeEnough) {
            // Desire to stop
            return desiredVelocity;
        }

        float desiredSpeed = boid.maxSpeed;

        // Desire to go in the direction of the goal as fast as possible
        if (dist > 0) {
            desiredVelocity.scl(1 / dist).scl(desiredSpeed);
        }

        return desiredVelocity;
    }

    public static Vector2 flee(Boid boid, Vector2 goal) {
        return seek(boid, goal).scl(-1);
    }

}
