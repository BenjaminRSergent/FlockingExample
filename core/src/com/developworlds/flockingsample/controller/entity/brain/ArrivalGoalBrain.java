package com.developworlds.flockingsample.controller.entity.brain;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.controller.entity.behavior.steering.SteeringMethods;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class ArrivalGoalBrain implements BoidAI {
    private Vector2 goal = new Vector2();
    private float slowdownRadius = 250;

    public void update(Boid boid, World world, float deltaTime) {
        SteeringMethods.arrive(boid, goal, slowdownRadius, boid.acceleration);
    }

    public void setGoal(Vector2 goal) {
        this.goal.set(goal);
    }

    public float getSlowdownRadius() {
        return slowdownRadius;
    }

    public void setSlowdownRadius(float slowdownRadius) {
        this.slowdownRadius = slowdownRadius;
    }

}
