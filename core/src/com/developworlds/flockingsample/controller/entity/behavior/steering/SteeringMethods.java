package com.developworlds.flockingsample.controller.entity.behavior.steering;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.entity.Boid;


// Note: Not thread safe! Make a vector2 pool if you need thread safety.
public class SteeringMethods {
    public static Vector2 arrive(Boid boid, Vector2 goal, float slowdownRadius, Vector2 force) {
        float closeEnough = Math.max(boid.size.x, boid.size.y);

        force.set(goal);
        force.sub(boid.position);

        float dist = force.len();

        if (dist < closeEnough) {
            // Desire to stop
            force.set(boid.velocity).scl(-1);
        }

        force.sub(boid.velocity);
        dist = force.len();

        float desiredSpeed = boid.maxAcceleration;

        if (dist < slowdownRadius) {
            desiredSpeed = boid.maxAcceleration * ((slowdownRadius - dist) / slowdownRadius);
        }

        return force.nor().scl(desiredSpeed);
    }

    public static Vector2 depart(Boid boid, Vector2 antiGoal, int farEnough, Vector2 force) {
        force.set(boid.position);
        force.sub(antiGoal);

        float dist = force.len2();

        if(dist < farEnough*farEnough) {
            flee(boid,antiGoal,force);
        } else {
            force.set(0,0);
        }

        return force;
    }

    public static Vector2 depart(Boid boid, Vector2 antiGoal, float slowdownRadius, float farEnough, Vector2 force) {
        float closeEnough = Math.max(boid.size.x, boid.size.y);

        force.set(boid.position);
        force.sub(antiGoal);

        float dist = force.len();

        if (dist > farEnough) {
            force.set(0, 0);
            return force;
        }

        force.sub(boid.velocity);
        dist = force.len();

        float desiredSpeed = boid.maxAcceleration;

        if (dist > slowdownRadius) {
            desiredSpeed = boid.maxAcceleration * (1 - ((dist - slowdownRadius) / slowdownRadius));
        }

        return force.nor().scl(desiredSpeed);
    }

    public static Vector2 flee(Boid boid, Vector2 antiGoal, Vector2 force) {
        return seek(boid, antiGoal, force).scl(-1);
    }

    public static Vector2 seek(Boid boid, Vector2 goal, Vector2 force) {
        float closeEnough = Math.max(boid.size.x, boid.size.y);

        force.set(goal);
        force.sub(boid.position);

        float dist = force.len();

        if (dist < closeEnough) {
            // Desire to stop
            force.set(boid.velocity).scl(-1);
        }

        force.sub(boid.velocity);
        dist = force.len();

        float desiredSpeed = boid.maxAcceleration;

        if (dist > 0) {
            force.scl(1 / dist).scl(desiredSpeed);
        }

        return force;
    }


}
