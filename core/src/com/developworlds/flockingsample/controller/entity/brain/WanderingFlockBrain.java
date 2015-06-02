package com.developworlds.flockingsample.controller.entity.brain;


import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.FlockingApplication;
import com.developworlds.flockingsample.controller.entity.behavior.goals.AvoidEdgeBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.FlockingBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.MatchHeadingBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.WanderBehavior;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class WanderingFlockBrain extends BoidAI {
    float SLOW_DOWN_RADIUS = 500;
    FlockingBehavior flockingBehavior = new FlockingBehavior();
    MatchHeadingBehavior headingBehavior = new MatchHeadingBehavior();
    WanderBehavior wanderingBehavior = new WanderBehavior();
    AvoidEdgeBehavior avoidEdgeBehavior = new AvoidEdgeBehavior();

    float flockScale = 0.5f;
    float wanderScale = 0.35f;
    float avoidScale = 0.75f;

    public void update(Boid boid, World world, float deltaTime) {
        Vector2 flockCenter = FlockingApplication.vectorPool.obtain();
        Vector2 wanderTarget = FlockingApplication.vectorPool.obtain();
        Vector2 headingTarget = FlockingApplication.vectorPool.obtain();
        Vector2 avoidTarget = FlockingApplication.vectorPool.obtain();
        Vector2 tmpVector = FlockingApplication.vectorPool.obtain();

        boid.acceleration.set(0, 0);

        float accelerationLeft = boid.maxAcceleration;

        avoidEdgeBehavior.getSteeringForce(boid, world, deltaTime, avoidTarget);
        boid.acceleration.add(avoidTarget.scl(avoidScale));

        accelerationLeft = boid.maxAcceleration - boid.acceleration.len();

        boolean accelMaxed = isAccelMax(boid);


        if (!accelMaxed) {
            wanderingBehavior.getSteeringForce(boid, world, deltaTime, wanderTarget);

            wanderTarget.scl(wanderScale);
            float amount = headingTarget.len();

            if (accelerationLeft < amount) {
                wanderTarget.nor().scl(accelerationLeft);
                amount = accelerationLeft;
            }


            accelMaxed |= isAccelMax(boid);

            boid.acceleration.add(wanderTarget);
            accelerationLeft = boid.maxAcceleration - boid.acceleration.len();
        }

        if (!accelMaxed) {
            flockingBehavior.getSteeringForce(boid, world, deltaTime, flockCenter);

            flockCenter.scl(flockScale);
            float amount = flockCenter.len();

            if (accelerationLeft < amount) {
                wanderTarget.nor().scl(accelerationLeft);
                amount = accelerationLeft;
            }


            boid.acceleration.add(flockCenter);
            accelerationLeft = boid.maxAcceleration - boid.acceleration.len();
            accelMaxed |= isAccelMax(boid);
        }


        if (!accelMaxed) {
            headingBehavior.getSteeringForce(boid, world, deltaTime, headingTarget);

            headingTarget.scl(flockScale);
            float amount = headingTarget.len();

            if (accelerationLeft < amount) {
                wanderTarget.nor().scl(accelerationLeft);
                amount = accelerationLeft;
            }




            boid.acceleration.add(headingTarget);
            accelerationLeft = boid.maxAcceleration - boid.acceleration.len();
            accelMaxed |= isAccelMax(boid);
        }

        if (boid.acceleration.x == 0 && boid.acceleration.y == 0) {
            int x = 0;
            System.out.print(x);
        }

        FlockingApplication.vectorPool.free(flockCenter);
        FlockingApplication.vectorPool.free(wanderTarget);
        FlockingApplication.vectorPool.free(headingTarget);
        FlockingApplication.vectorPool.free(avoidTarget);
        FlockingApplication.vectorPool.free(tmpVector);
    }

    private boolean isAccelMax(Boid boid) {
        return boid.maxAcceleration * boid.maxAcceleration < boid.acceleration.len2();
    }

}