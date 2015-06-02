package com.developworlds.flockingsample.controller.entity.behavior.goals;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

import java.util.List;

public class FlockingBehavior implements Behavior {
    float DEF_RADIUS = 200;
    Circle range = new Circle();

    public FlockingBehavior() {
        range.setRadius(DEF_RADIUS);
    }

    @Override
    public Vector2 getTarget(Boid boid, World world, float deltaTime, Vector2 target) {
        range.setPosition(boid.position.x, boid.position.y);

        target.set(0, 0);

        List<Boid> boids = world.getBoidsInRange(range);

        for (int index = 0; index < boids.size(); index++) {
            Boid flockmate = boids.get(index);
            if (!flockmate.equals(this)) {
                target.add(flockmate.position);
            }
        }

        if (boids.size() > 0) {
            target.scl(1 / (float)boids.size());
        } else {
            target.set(boid.position);
        }

        return target;
    }
}
