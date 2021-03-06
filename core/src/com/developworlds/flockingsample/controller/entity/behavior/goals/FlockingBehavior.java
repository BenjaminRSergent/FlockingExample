package com.developworlds.flockingsample.controller.entity.behavior.goals;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.controller.entity.behavior.steering.SteeringMethods;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

import java.util.List;

public class FlockingBehavior implements Behavior {
    public final float DEF_RADIUS = 160;
    private Circle range = new Circle();

    public FlockingBehavior() {
        range.setRadius(DEF_RADIUS);
    }

    private Vector2 target = new Vector2();
    @Override
    public Vector2 getSteeringForce(Boid boid, World world, float deltaTime, Vector2 force) {
        range.setPosition(boid.position.x, boid.position.y);

        force.set(0,0);
        target.set(0, 0);

        List<Boid> boids = world.getBoidsInRange(range);

        int size = boids.size();
        for (int index = 0; index < size; index++) {
            Boid flockmate = boids.get(index);
            if (!flockmate.equals(boid)) {
                target.add(flockmate.position);
            }
        }

        if (boids.size() > 1) {
            target.scl(1 / (float) (boids.size() - 1));
        } else {
            return force;
        }

        SteeringMethods.seek(boid, target, force);

        return force;
    }
}
