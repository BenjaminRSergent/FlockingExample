package com.developworlds.flockingsample.controller.entity.brain;


import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.controller.entity.behavior.goals.AvoidEdgeBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.FlockingBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.MatchHeadingBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.SeperationBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.WanderBehavior;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class WanderingFlockBrain extends BoidAI {
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

    Vector2 flockCenter = new Vector2();
    Vector2 wanderForce = new Vector2();
    Vector2 seperationForce = new Vector2();
    Vector2 headingForce = new Vector2();
    Vector2 avoidForce = new Vector2();
    Vector2 tmpVector = new Vector2();

    public void update(Boid boid, World world, float deltaTime) {
        boid.acceleration.set(0, 0);

        avoidEdgeBehavior.getSteeringForce(boid, world, deltaTime, avoidForce);
        boid.acceleration.add(avoidForce.scl(avoidScale));

        float accelerationLeft = boid.maxAcceleration - boid.acceleration.len();

        if (accelerationLeft > 0) {
            seperationBehavior.getSteeringForce(boid, world, deltaTime, seperationForce);
            boid.acceleration.add(seperationForce.scl(seperationScale));

            float amount = seperationForce.len();

            if (accelerationLeft < amount) {
                seperationForce.nor().scl(accelerationLeft);
            }

            boid.acceleration.add(seperationForce);
            accelerationLeft = boid.maxAcceleration - boid.acceleration.len();
        }

        if (accelerationLeft > 0) {
            wanderingBehavior.getSteeringForce(boid, world, deltaTime, wanderForce);

            wanderForce.scl(wanderScale);
            float amount = headingForce.len();

            if (accelerationLeft < amount) {
                wanderForce.nor().scl(accelerationLeft);
            }

            boid.acceleration.add(wanderForce);
            accelerationLeft = boid.maxAcceleration - boid.acceleration.len();
        }

        if (accelerationLeft > 0) {
            flockingBehavior.getSteeringForce(boid, world, deltaTime, flockCenter);

            flockCenter.scl(flockScale);
            float amount = flockCenter.len();

            if (accelerationLeft < amount) {
                wanderForce.nor().scl(accelerationLeft);
            }

            boid.acceleration.add(flockCenter);
            accelerationLeft = boid.maxAcceleration - boid.acceleration.len();
        }


        if (accelerationLeft > 0) {
            headingBehavior.getSteeringForce(boid, world, deltaTime, headingForce);

            headingForce.scl(flockScale);
            float amount = headingForce.len();

            if (accelerationLeft < amount) {
                wanderForce.nor().scl(accelerationLeft);
            }

            boid.acceleration.add(headingForce);
        }

        if (boid.acceleration.x == 0 && boid.acceleration.y == 0) {
            int x = 0;
            System.out.print(x);
        }
    }

}
