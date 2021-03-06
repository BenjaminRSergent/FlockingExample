package com.developworlds.flockingsample.controller.entity.behavior.goals;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

import java.util.List;


public class MatchHeadingBehavior implements Behavior {
    float DEF_RADIUS = 120;
    Circle range = new Circle();

    public MatchHeadingBehavior() {
        range.setRadius(DEF_RADIUS);
    }

    Vector2 boidHeading = new Vector2();
    @Override
    public Vector2 getSteeringForce(Boid boid, World world, float deltaTime, Vector2 force) {
        range.setPosition(boid.position.x, boid.position.y);

        force.set(0, 0);

        List<Boid> boids = world.getBoidsInRange(range);

        int size = boids.size();
        for (int index = 0; index < size; index++) {
            Boid flockmate = boids.get(index);
            if (!flockmate.equals(boid)) {
                boidHeading.set(boid.velocity).nor();
                force.add(boidHeading);
            }
        }

        if (boids.size() > 1) {
            force.scl(1 / (float) (boids.size() - 1));
        } else {
            force.set(0, 0);
            return force;
        }

        boidHeading.set(boid.velocity).nor();
        force.sub(boidHeading).nor().scl(boid.maxAcceleration);

        return force.add(force);
    }
}
