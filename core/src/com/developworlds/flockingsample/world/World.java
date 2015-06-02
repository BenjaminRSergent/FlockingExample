package com.developworlds.flockingsample.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.developworlds.flockingsample.world.entity.Boid;

import java.util.ArrayList;

public class World {
    private ArrayList<Boid> boids = new ArrayList<Boid>();

    // TODO: Quadtrees?
    public ArrayList<Boid> getBoidsInRange(Circle circle) {
        // Naive implementation for now.
        ArrayList<Boid> inRange = new ArrayList<Boid>(boids.size());
        for (int index = 0; index < boids.size(); index++) {
            Boid boid = boids.get(index);
            if (boid.isInside(circle)) {
                inRange.add(boid);
            }
        }

        return inRange;
    }

    public void draw(SpriteBatch batch) {
        for (int index = 0; index < boids.size(); index++) {
            boids.get(index).draw(batch);
        }
    }


    public void update(float deltaTime) {
        for (int index = 0; index < boids.size(); index++) {
            boids.get(index).update(this, deltaTime);
        }
    }

    public void addBoid(Boid boid) {
        boids.add(boid);
    }

    public void removeBoid(Boid boid) {
        boids.remove(boid);
    }

    public ArrayList<Boid> getBoids() {
        return boids;
    }
}
