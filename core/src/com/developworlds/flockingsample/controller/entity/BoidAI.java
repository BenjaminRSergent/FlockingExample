package com.developworlds.flockingsample.controller.entity;

import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

/**
 * Created by benjamin-sergent on 5/30/15.
 */
public abstract class BoidAI {
    public abstract void update(Boid boid, World world, float deltaTime);
}
