package com.developworlds.flockingsample;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.developworlds.flockingsample.controller.entity.behavior.goals.FlockingBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.MatchHeadingBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.SeperationBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.WanderBehavior;
import com.developworlds.flockingsample.controller.entity.brain.BoidAI;
import com.developworlds.flockingsample.controller.entity.brain.ComplexBrain;
import com.developworlds.flockingsample.controller.entity.locomotion.Locomotion;
import com.developworlds.flockingsample.controller.entity.locomotion.WrappingLocomotion;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;


public class FlockingApplication extends ApplicationAdapter {
    private SpriteBatch batch;
    private World world;
    private boolean running = false;
    private BoidAI brain;
    private Locomotion locomotion;

    @Override
    public void create() {
        batch = new SpriteBatch();
        world = new World(new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        ComplexBrain complexBrain = new ComplexBrain();

        complexBrain.addBehavior(new SeperationBehavior(), 0.5f, 0);
        complexBrain.addBehavior(new FlockingBehavior(), 0.25f, 1);
        complexBrain.addBehavior(new MatchHeadingBehavior(), 0.25f, 2);
        complexBrain.addBehavior(new WanderBehavior(), 0.5f, 3);

        brain = complexBrain;

        locomotion = new WrappingLocomotion(world.getBounds());

        new Thread(updater).start();
    }

    private void addBoids(int numToAdd) {
        for (int index = 0; index < numToAdd; index++) {
            Boid boid = new Boid();
            boid.position.set((float) (Gdx.graphics.getWidth() * Math.random()), (float) (Gdx.graphics.getHeight() * Math.random()));
            boid.setBoidAi(brain);
            boid.setLocomotion(locomotion);
            world.addBoid(boid);
        }
    }

    float accumulator = 0;
    final float SEC_PER_FRAME = 1 / 30.0f;

    @Override
    public void render() {

        if (Gdx.input.justTouched()) {
            if (!running) {
                addBoids(300);
            }
            running = true;
        }
        if (running) {
            accumulator += Gdx.graphics.getDeltaTime();
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        world.draw(batch);
        batch.end();
    }


    public Runnable updater = new Runnable() {
        public void run() {
            while (true) {
                try {
                    while (accumulator > SEC_PER_FRAME) {
                        if (running) {
                            world.update(SEC_PER_FRAME);
                        }
                        accumulator -= SEC_PER_FRAME;
                    }
                } catch (Exception ex) {

                    Gdx.app.log("UPDATE", "Update error " + ex, ex);
                }
                try {

                    Thread.sleep((long) (2)); // Minisleep to avoid fully hogging resources.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    };


}
