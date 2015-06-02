package com.developworlds.flockingsample.controller.entity.behavior.goals;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.controller.entity.behavior.steering.SteeringMethods;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

import java.util.List;


public class MatchHeadingBehavior implements Behavior {
    float DEF_RADIUS = Boid.DEF_SIZE*4;
    Circle range = new Circle();

    public MatchHeadingBehavior() {
        range.setRadius(DEF_RADIUS);
    }


    Vector2 target = new Vector2();

    @Override
    public Vector2 getSteeringForce(Boid boid, World world, float deltaTime, Vector2 force) {
        range.setPosition(boid.position.x, boid.position.y);

        target.set(0, 0);

        List<Boid> boids = world.getBoidsInRange(range);

        int size = boids.size();
        for (int index = 0; index < size; index++) {
            Boid flockmate = boids.get(index);
            if (!flockmate.equals(this)) {
                target.add(flockmate.velocity);
            }
        }

        if (boids.size() > 0) {
            target.scl(1 / (float) boids.size());
        } else {
            force.set(0, 0);
            return force;
        }

        target.nor().scl(boid.getRadius() * 10).add(boid.position);

        SteeringMethods.seek(boid, target, force);

        return force;
    }
}
