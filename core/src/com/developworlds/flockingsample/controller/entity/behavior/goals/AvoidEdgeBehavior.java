package com.developworlds.flockingsample.controller.entity.behavior.goals;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.controller.entity.behavior.steering.SteeringMethods;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class AvoidEdgeBehavior implements Behavior {
    float tooClose = Boid.DEF_SIZE * 10;

    Vector2 target = new Vector2();

    public Vector2 getSteeringForce(Boid boid, World world, float deltaTime, Vector2 force) {
        target.set(boid.position);

        float distanceFromTrigger = 0;

        float targetDist = boid.getRadius() * 2;
        if (boid.position.x < tooClose) {
            target.add(targetDist, 0);
            distanceFromTrigger = tooClose - boid.position.x;
        }

        if (boid.position.y < tooClose) {
            target.add(0, targetDist);
            distanceFromTrigger = Math.max(distanceFromTrigger, tooClose - boid.position.y);
        }

        if (boid.position.x > world.getBounds().width - tooClose) {
            target.add(-targetDist, 0);
            distanceFromTrigger = Math.max(distanceFromTrigger, boid.position.x - (world.getBounds().width - tooClose));
        }

        if (boid.position.y > world.getBounds().height - tooClose) {
            target.add(0, -targetDist);
            distanceFromTrigger = Math.max(distanceFromTrigger, boid.position.y - (world.getBounds().height - tooClose));
        }

        if (distanceFromTrigger == 0) {
            force.set(0, 0);
            return force;
        }

        SteeringMethods.seek(boid, target, force);

        float forceScale = (distanceFromTrigger) / tooClose;
        force.scl(forceScale);

        return force;
    }


}
