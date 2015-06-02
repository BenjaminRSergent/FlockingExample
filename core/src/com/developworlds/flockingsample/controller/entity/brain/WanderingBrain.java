package com.developworlds.flockingsample.controller.entity.brain;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.controller.entity.behavior.goals.WanderBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.steering.SteeringMethods;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class WanderingBrain extends BoidAI {
    private WanderBehavior wanderBehavior = new WanderBehavior();
    Vector2 target = new Vector2();
    @Override
    public void update(Boid boid, World world, float deltaTime) {
        wanderBehavior.getTarget(boid, world, deltaTime, target);
        SteeringMethods.seek(boid, target, boid.desiredVelocity);
    }
}
