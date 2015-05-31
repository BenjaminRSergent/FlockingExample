package com.developworlds.flockingsample;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.developworlds.flockingsample.controller.entity.BasicLocomotion;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class FlockingApplication extends ApplicationAdapter {
    SpriteBatch batch;
    private World world;


    @Override
    public void create() {
        batch = new SpriteBatch();
        world = new World();
        Boid boid = new Boid();
        boid.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        boid.goal.set(0, 0);
        boid.setLocomotion(new BasicLocomotion());

        world.addBoid(boid);
    }

    @Override
    public void render() {
        update();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        world.draw(batch);
        batch.end();
    }

    public void update() {
        world.update(Gdx.graphics.getDeltaTime());
    }
}
