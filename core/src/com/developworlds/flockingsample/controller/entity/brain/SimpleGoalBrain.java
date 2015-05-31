package com.developworlds.flockingsample.controller.entity.brain;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class SimpleGoalBrain extends BoidAI {
    private Vector2 goal = new Vector2();
    private Vector2 desiredVelocity = new Vector2();

    public void update(Boid boid, World world, float deltaTime) {
        boid.desiredVelocity.set(boid.desiredVelocity.set(com.developworlds.flockingsample.controller.entity.brain.behavior.Behaviors.seek(boid, goal)));
    }

    public void setGoal(Vector2 goal) {
        this.goal.set(goal);
    }
}
