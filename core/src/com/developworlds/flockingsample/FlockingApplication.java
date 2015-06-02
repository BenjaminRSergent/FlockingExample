package com.developworlds.flockingsample;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.developworlds.flockingsample.controller.entity.brain.BoidAI;
import com.developworlds.flockingsample.controller.entity.brain.WanderingFlockBrain;
import com.developworlds.flockingsample.controller.entity.locomotion.BasicLocomotion;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;


public class FlockingApplication extends ApplicationAdapter {
    private SpriteBatch batch;
    private World world;
    private boolean running = false;

    @Override
    public void create() {
        batch = new SpriteBatch();
        world = new World(new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

    }

    private void addBoids(int numToAdd) {
        for (int index = 0; index < numToAdd; index++) {
            Boid boid = new Boid();
            boid.position.set((float) (Gdx.graphics.getWidth() * Math.random()), (float) (Gdx.graphics.getHeight() * Math.random()));
            boid.setLocomotion(new BasicLocomotion());
            BoidAI brain = new WanderingFlockBrain();
            boid.setBoidAi(brain);
            world.addBoid(boid);
        }
    }

    @Override
    public void render() {
        if (Gdx.input.justTouched()) {
            if(!running) {
                addBoids(300);
            }
            running = true;
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

    float accumulator = 0;
    final float SEC_PER_FRAME = 1/30.0f;
    private void update() {
        accumulator += Gdx.graphics.getDeltaTime();
        try {
            while(accumulator > SEC_PER_FRAME) {
                world.update(SEC_PER_FRAME);
                accumulator -= SEC_PER_FRAME;
            }
        }catch (Exception ex) {

            Gdx.app.log("UPDATE", "Update error " + ex, ex);
        }
    }


}
