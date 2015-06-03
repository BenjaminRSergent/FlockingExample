package com.developworlds.flockingsample.controller.entity.behavior.goals;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

import java.util.List;

public class SeperationBehavior implements Behavior {
    float DEF_RADIUS = Boid.DEF_SIZE * 2;
    Circle range = new Circle();

    public SeperationBehavior() {
        range.setRadius(DEF_RADIUS);
    }

    Vector2 target = new Vector2();
    Vector2 memberToBoid = new Vector2();

    @Override
    public Vector2 getSteeringForce(Boid boid, World world, float deltaTime, Vector2 force) {
        range.setPosition(boid.position.x, boid.position.y);

        target.set(0, 0);
        force.set(0,0);
        List<Boid> boids = world.getBoidsInRange(range);

        int size = boids.size();
        for (int index = 0; index < size; index++) {
            Boid flockmate = boids.get(index);
            if (!flockmate.equals(boid)) {
                memberToBoid.set(boid.position);
                memberToBoid.sub(flockmate.position);
                float toMember = memberToBoid.len();

                memberToBoid.scl(boid.maxAcceleration / toMember).scl(Boid.DEF_SIZE / toMember);
                force.add(memberToBoid);
            }
        }

        return force;
    }
}
