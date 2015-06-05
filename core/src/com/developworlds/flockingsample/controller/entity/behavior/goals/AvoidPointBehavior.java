package com.developworlds.flockingsample.controller.entity.behavior.goals;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.controller.entity.behavior.steering.SteeringMethods;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class AvoidPointBehavior implements Behavior {
    private Vector2 antiGoal = new Vector2();
    private int departRadius;
    private boolean active;

    @Override
    public Vector2 getSteeringForce(Boid boid, World world, float deltaTime, Vector2 force) {
        force.set(0, 0);


        if (active) {
            SteeringMethods.depart(boid, antiGoal, departRadius, force);
        }

        return force;
    }

    public void setDepartRadius(int radius) {
        this.departRadius = radius;
    }

    public void setAntiGoal(int x, int y) {
        this.antiGoal.set(x, y);
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
