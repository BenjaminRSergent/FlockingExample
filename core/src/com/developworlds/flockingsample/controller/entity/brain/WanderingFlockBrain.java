package com.developworlds.flockingsample.controller.entity.brain;


import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.FlockingApplication;
import com.developworlds.flockingsample.controller.entity.behavior.goals.AvoidEdgeBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.FlockingBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.MatchHeadingBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.WanderBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.steering.SteeringMethods;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class WanderingFlockBrain extends BoidAI {
    float SLOW_DOWN_RADIUS = 500;
    FlockingBehavior flockingBehavior = new FlockingBehavior();
    MatchHeadingBehavior headingBehavior = new MatchHeadingBehavior();
    WanderBehavior wanderingBehavior = new WanderBehavior();
    AvoidEdgeBehavior avoidEdgeBehavior = new AvoidEdgeBehavior();



    public void update(Boid boid, World world, float deltaTime) {
        Vector2 flockCenter = FlockingApplication.vectorPool.obtain();
        Vector2 wanderTarget = FlockingApplication.vectorPool.obtain();
        Vector2 headingTarget = FlockingApplication.vectorPool.obtain();
        Vector2 avoidTarget = FlockingApplication.vectorPool.obtain();
        Vector2 tmpVector = FlockingApplication.vectorPool.obtain();

        flockingBehavior.getTarget(boid, world, deltaTime, flockCenter);
        wanderingBehavior.getTarget(boid, world, deltaTime, wanderTarget);
        headingBehavior.getTarget(boid, world, deltaTime, headingTarget);
        avoidEdgeBehavior.getTarget(boid, world, deltaTime, avoidTarget);

        tmpVector.set(0, 0);
        if (!flockCenter.epsilonEquals(boid.position, 0.01f)) {
            SteeringMethods.arrive(boid, flockCenter, SLOW_DOWN_RADIUS, boid.desiredVelocity);
            tmpVector.add(boid.desiredVelocity);
        }

        if (!headingTarget.epsilonEquals(boid.position, 0.01f)) {
            SteeringMethods.seek(boid, headingTarget, boid.desiredVelocity);
            tmpVector.add(boid.desiredVelocity);
        }

        if (!avoidTarget.epsilonEquals(boid.position, 0.01f)) {
            SteeringMethods.seek(boid, avoidTarget, boid.desiredVelocity);
            tmpVector.add(boid.desiredVelocity.scl(1.3f));
        }

        SteeringMethods.arrive(boid, wanderTarget, SLOW_DOWN_RADIUS, boid.desiredVelocity);

        tmpVector.add(boid.desiredVelocity.scl(1.5f));

        tmpVector.nor().scl(boid.maxSpeed);

        boid.desiredVelocity.set(tmpVector);

        FlockingApplication.vectorPool.free(flockCenter);
        FlockingApplication.vectorPool.free(wanderTarget);
        FlockingApplication.vectorPool.free(headingTarget);
        FlockingApplication.vectorPool.free(avoidTarget);
        FlockingApplication.vectorPool.free(tmpVector);
    }

}