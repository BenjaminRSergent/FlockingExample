package com.developworlds.flockingsample.controller.entity.brain;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.controller.entity.behavior.goals.Behavior;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

import java.util.ArrayList;

public class ComplexBrain implements BoidAI {
    public static class BehaviorData {
        Behavior behavior;
        float scale;
        int priority; // Lower is higher priority

        public BehaviorData(Behavior behavior, float scale, int priority) {
            this.behavior = behavior;
            this.scale = scale;
            this.priority = priority;
        }
    }

    ArrayList<BehaviorData> behaviors = new ArrayList<BehaviorData>();

    Vector2 tmpVector = new Vector2();

    public void update(Boid boid, World world, float deltaTime) {
        boid.acceleration.set(0, 0);

        for(int index = 0; index < behaviors.size(); index++) {
            BehaviorData data = behaviors.get(index);
            integrateBehavior(data.behavior, boid, world, deltaTime, data.scale);
        }

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

    public void addBehavior(Behavior behavior, float scale, int priority) {
        BehaviorData data = new BehaviorData(behavior,scale,priority);
        for(int index = 0; index < behaviors.size(); index++) {
            BehaviorData otherBehavior = behaviors.get(index);

            if(data.priority < otherBehavior.priority) {
                behaviors.add(index, data);
                return;
            }
        }

        behaviors.add(data);
    }
}
