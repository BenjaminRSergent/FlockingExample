package com.developworlds.flockingsample.controller.entity.brain;


import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.controller.entity.behavior.goals.AvoidEdgeBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.Behavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.FlockingBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.MatchHeadingBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.SeperationBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.WanderBehavior;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class ComplexFlockBrain extends BoidAI {
    float SLOW_DOWN_RADIUS = 500;
    FlockingBehavior flockingBehavior = new FlockingBehavior();
    MatchHeadingBehavior headingBehavior = new MatchHeadingBehavior();
    WanderBehavior wanderingBehavior = new WanderBehavior();
    AvoidEdgeBehavior avoidEdgeBehavior = new AvoidEdgeBehavior();
    SeperationBehavior seperationBehavior = new SeperationBehavior();

    float flockScale = 0.5f;
    float wanderScale = 0.35f;
    float avoidScale = 0.9f;
    float seperationScale = 0.9f;

    Vector2 tmpVector = new Vector2();

    public void update(Boid boid, World world, float deltaTime) {
        boid.acceleration.set(0, 0);

        integrateBehavior(avoidEdgeBehavior, boid, world, deltaTime, avoidScale);
        integrateBehavior(seperationBehavior, boid, world, deltaTime, avoidScale);
        integrateBehavior(wanderingBehavior, boid, world, deltaTime, avoidScale);
        integrateBehavior(headingBehavior, boid, world, deltaTime, avoidScale);
        integrateBehavior(flockingBehavior, boid, world, deltaTime, avoidScale);
    }

    public void integrateBehavior(Behavior behavior, Boid boid, World world, float deltaTime, float scale) {
        float accelerationLeft = getAccelerationLeft(boid);
        if (accelerationLeft > 0) {
            behavior.getSteeringForce(boid, world, deltaTime, tmpVector);
            tmpVector.scl(scale);
            float amount = tmpVector.len();

            if (accelerationLeft < amount) {
                tmpVector.nor().scl(accelerationLeft);
            }
            
            boid.acceleration.add(tmpVector);
        }
    }

    private float getAccelerationLeft(Boid boid) {
        return boid.maxAcceleration - boid.acceleration.len();
    }
}
