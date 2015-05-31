package com.developworlds.flockingsample.controller.entity.brain;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class RandomSeekerBrain extends BoidAI {
    private Vector2 goal = new Vector2();
    private float slowdownRadius = 250;
    private Rectangle bounds;

    public RandomSeekerBrain(Rectangle bounds) {
        this.bounds = bounds;
        setGoalInBounds();
    }

    public void update(Boid boid, World world, float deltaTime) {
        if (isCloseToGoal(boid)) {
            setGoalInBounds();
        }

        boid.desiredVelocity.set(Behaviors.seek(boid, goal));
    }

    private boolean isCloseToGoal(Boid boid) {
        return goal.cpy().sub(boid.position).len2() < boid.getRadiusSq();
    }

    public void setGoalInBounds() {
        goal.x = (float) (Math.random() * (bounds.width - bounds.x) + bounds.x);
        goal.y = (float) (Math.random() * (bounds.height - bounds.y) + bounds.y);
    }
}
