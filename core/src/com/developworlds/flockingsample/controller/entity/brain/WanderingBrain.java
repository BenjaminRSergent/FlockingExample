package com.developworlds.flockingsample.controller.entity.brain;

import com.developworlds.flockingsample.controller.entity.behavior.goals.WanderBehavior;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class WanderingBrain implements BoidAI {
    private WanderBehavior wanderBehavior = new WanderBehavior();
    @Override
    public void update(Boid boid, World world, float deltaTime) {
        wanderBehavior.getSteeringForce(boid, world, deltaTime, boid.acceleration);
    }
}
