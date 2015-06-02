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

        float accelerationLeft = getAccelerationLeft(boid);
        addBehavior(avoidEdgeBehavior, boid, world, deltaTime, avoidScale, accelerationLeft);
        addBehavior(seperationBehavior, boid, world, deltaTime, avoidScale, accelerationLeft);
        addBehavior(wanderingBehavior, boid, world, deltaTime, avoidScale, accelerationLeft);
        addBehavior(headingBehavior, boid, world, deltaTime, avoidScale, accelerationLeft);
        addBehavior(flockingBehavior, boid, world, deltaTime, avoidScale, accelerationLeft);
    }

    private float getAccelerationLeft(Boid boid) {
        return boid.maxAcceleration - boid.acceleration.len();
    }

    public void addBehavior(Behavior behavior, Boid boid, World world, float deltaTime, float scale, float accelerationLeft) {
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

}
