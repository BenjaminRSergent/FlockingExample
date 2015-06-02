package com.developworlds.flockingsample.controller.entity.locomotion;

import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class BasicLocomotion extends Locomotion {
    // The simplest locomotion attempts to match the desired velocity
    // without worrying about interpenetration or other movement rules.
    @Override
    public void update(Boid boid, World world, float deltaTime) {

        Locomotion.integrate(boid, deltaTime);
    }
}
