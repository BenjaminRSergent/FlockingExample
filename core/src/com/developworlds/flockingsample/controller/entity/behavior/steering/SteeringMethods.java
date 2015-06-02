package com.developworlds.flockingsample.controller.entity.behavior.steering;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.entity.Boid;


// Note: Not thread safe! Make a vector2 pool if you need thread safety.
public class SteeringMethods {
    public static Vector2 arrive(Boid boid, Vector2 goal, float slowdownRadius, Vector2 desiredVelocity) {
        float closeEnough = Math.max(boid.size.x, boid.size.y);

        desiredVelocity.set(goal);
        desiredVelocity.sub(boid.position);

        float dist = desiredVelocity.len();

        if (dist < closeEnough) {
            // Desire to stop
            desiredVelocity.set(0, 0);
            return desiredVelocity;
        }

        float desiredSpeed = boid.maxAcceleration;
        if (dist < slowdownRadius) {
            desiredSpeed = boid.maxAcceleration * ((slowdownRadius - dist) / slowdownRadius);
        }


        // Desire to go in the direction of the goal as fast as possible

        if (dist > 0) {
            desiredVelocity.scl(1 / dist).scl(desiredSpeed);
        }

        return desiredVelocity.sub(boid.velocity);
    }

    public static Vector2 depart(Boid boid, Vector2 antiGoal, float slowdownRadius, float farEnough, Vector2 desiredVelocity) {
        desiredVelocity.set(antiGoal.cpy());
        desiredVelocity.sub(boid.position);

        float dist = desiredVelocity.len();

        float desiredSpeed = boid.maxAcceleration;

        if (dist > slowdownRadius) {
            desiredSpeed = boid.maxAcceleration * ((farEnough - dist) / (farEnough - slowdownRadius));
        }


        // Desire to go in the direction of the goal as fast as possible

        if (dist > 0) {
            desiredVelocity.scl(1 / dist).scl(desiredSpeed);
        }

        return desiredVelocity.sub(boid.velocity);
    }

    public static Vector2 flee(Boid boid, Vector2 antiGoal, Vector2 desiredVelocity) {
        return seek(boid, antiGoal,desiredVelocity).scl(-1);
    }

    public static Vector2 seek(Boid boid, Vector2 goal, Vector2 desiredVelocity) {
        float closeEnough = Math.max(boid.size.x, boid.size.y);

        desiredVelocity.set(goal);
        desiredVelocity.sub(boid.position);

        float dist = desiredVelocity.len();

        if (dist < closeEnough) {
            // Desire to stop
            desiredVelocity.set(boid.velocity).scl(-1);
        }

        desiredVelocity.sub(boid.velocity);
        dist = desiredVelocity.len();

        float desiredSpeed = boid.maxAcceleration;

        if (dist > 0) {
            desiredVelocity.scl(1 / dist).scl(desiredSpeed);
        }

        return desiredVelocity;
    }

}
