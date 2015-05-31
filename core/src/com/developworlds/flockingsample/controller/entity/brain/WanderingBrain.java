package com.developworlds.flockingsample.controller.entity.brain;

import com.developworlds.flockingsample.controller.entity.brain.behavior.Behaviors;
import com.developworlds.flockingsample.controller.entity.brain.behavior.WanderBehavior;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class WanderingBrain extends BoidAI {
    WanderBehavior wanderBehavior = new WanderBehavior();

    @Override
    public void update(Boid boid, World world, float deltaTime) {
        boid.desiredVelocity.set(Behaviors.seek(boid, wanderBehavior.getWanderTarget(boid, deltaTime)));
    }
}
