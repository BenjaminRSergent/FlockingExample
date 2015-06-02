package com.developworlds.flockingsample.controller.entity.brain;


import com.badlogic.gdx.math.Vector2;
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
    Vector2 flockCenter = new Vector2();
    Vector2 wanderTarget = new Vector2();
    Vector2 headingTarget = new Vector2();

    Vector2 tmpVector = new Vector2();

    public void update(Boid boid, World world, float deltaTime) {
        flockingBehavior.getTarget(boid, world, deltaTime, flockCenter);
        wanderingBehavior.getTarget(boid, world, deltaTime, wanderTarget);
        headingBehavior.getTarget(boid, world, deltaTime, headingTarget);

        SteeringMethods.arrive(boid, flockCenter, SLOW_DOWN_RADIUS, boid.desiredVelocity);
        tmpVector.set(boid.desiredVelocity);

        SteeringMethods.seek(boid, headingTarget, boid.desiredVelocity);

        tmpVector.add(boid.desiredVelocity);
        tmpVector.scl(0.75f);

        SteeringMethods.arrive(boid, wanderTarget, SLOW_DOWN_RADIUS, boid.desiredVelocity);

        tmpVector.add(boid.desiredVelocity);
        tmpVector.nor().scl(boid.maxSpeed);

        boid.desiredVelocity.set(tmpVector);
    }

}