package com.developworlds.flockingsample.controller.entity.brain;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class SimpleGoalBrain extends BoidAI {
    private Vector2 goal = new Vector2();
    private Vector2 desiredVelocity = new Vector2();

    public void update(Boid boid, World world, float deltaTime) {
        boid.desiredVelocity.set(boid.desiredVelocity.set(Behaviors.seek(boid, goal)));
    }

    public void setGoal(Vector2 goal) {
        this.goal.set(goal);
    }

    public void setGoalInBounds(Rectangle bounds) {
        goal.x = (float) (Math.random() * (bounds.width - bounds.x) + bounds.x);
        goal.y = (float) (Math.random() * (bounds.height - bounds.y) + bounds.y);
    }
}
