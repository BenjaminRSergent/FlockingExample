package com.developworlds.flockingsample.controller.entity;

import com.developworlds.flockingsample.world.entity.Boid;

/**
 * Created by benjamin-sergent on 5/30/15.
 */
public abstract class Locomotion {
    public abstract void update(Boid boid, float deltaTime);
}
