package com.developworlds.flockingsample;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.developworlds.flockingsample.controller.entity.brain.WanderingBrain;
import com.developworlds.flockingsample.controller.entity.locomotion.WrappingLocomotion;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;

public class FlockingApplication extends ApplicationAdapter {
    private SpriteBatch batch;
    private World world;
    private boolean running = false;

    @Override
    public void create() {
        batch = new SpriteBatch();
        world = new World();
        addBoids(1);
    }

    private void addBoids(int numToAdd) {
        for (int index = 0; index < numToAdd; index++) {
            Boid boid = new Boid();
            boid.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            boid.setLocomotion(new WrappingLocomotion(new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));
            WanderingBrain brain = new WanderingBrain();
            boid.setBoidAi(brain);
            world.addBoid(boid);
        }
    }

    @Override
    public void render() {
        if (Gdx.input.justTouched()) {
            running = !running;
        }
        if (running) {
            update();
        }

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
