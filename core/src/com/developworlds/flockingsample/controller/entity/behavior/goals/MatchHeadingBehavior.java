package com.developworlds.flockingsample.controller.entity.behavior.goals;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

import java.util.List;

/**
 * Created by benjamin-sergent on 6/2/15.
 */
public class MatchHeadingBehavior implements Behavior {
    float DEF_RADIUS = 100;
    Circle range = new Circle();

    public MatchHeadingBehavior() {
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
                target.add(flockmate.velocity);
            }
        }

        if (boids.size() > 0) {
            target.scl(1 / (float) boids.size());
        }

        target.add(boid.position);


        return target;
    }
}
