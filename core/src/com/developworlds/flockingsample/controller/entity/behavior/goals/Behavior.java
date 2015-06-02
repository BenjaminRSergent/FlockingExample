package com.developworlds.flockingsample.controller.entity.behavior.goals;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public interface Behavior {
    public Vector2 getSteeringForce(Boid boid, World world, float deltaTime, Vector2 target);
}
