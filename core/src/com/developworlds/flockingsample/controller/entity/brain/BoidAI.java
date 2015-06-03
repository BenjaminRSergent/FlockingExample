package com.developworlds.flockingsample.controller.entity.brain;

import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public interface BoidAI {
    void update(Boid boid, World world, float deltaTime);
}
