package com.developworlds.flockingsample.controller.entity.behavior.goals;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class AvoidEdgeBehavior implements Behavior{
    float tooClose = 300;

    public Vector2 getTarget(Boid boid, World world, float deltaTime, Vector2 target) {
        target.set(boid.position);
        if(boid.position.x < tooClose) {
            target.add(1000, 0);
        }

        if(boid.position.y < tooClose) {
            target.add(0, 1000);
        }

        if(boid.position.x > world.getBounds().width - tooClose) {
            target.add(-1000, 0);
        }

        if(boid.position.y > world.getBounds().height -  tooClose) {
            target.add(0, -1000);
        }

        return target;
    }



}
