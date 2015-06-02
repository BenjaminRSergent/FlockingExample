package com.developworlds.flockingsample.controller.entity.behavior.goals;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.FlockingApplication;
import com.developworlds.flockingsample.controller.entity.behavior.steering.SteeringMethods;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

import java.util.List;

public class FlockingBehavior implements Behavior {
    float DEF_RADIUS = 150;
    Circle range = new Circle();

    public FlockingBehavior() {
        range.setRadius(DEF_RADIUS);
    }

    @Override
    public Vector2 getSteeringForce(Boid boid, World world, float deltaTime, Vector2 force) {
        Vector2 target = FlockingApplication.vectorPool.obtain();
        range.setPosition(boid.position.x, boid.position.y);

        target.set(0, 0);

        List<Boid> boids = world.getBoidsInRange(range);

        for (int index = 0; index < boids.size(); index++) {
            Boid flockmate = boids.get(index);
            if (!flockmate.equals(this)) {
                target.add(flockmate.position);
            }
        }

        if (boids.size() > 1) {
            target.scl(1 / (float) boids.size());
        } else {
            force.set(0, 0);
            return force;
        }

        FlockingApplication.vectorPool.free(target);

        SteeringMethods.seek(boid, target, force);

        return force;
    }
}
