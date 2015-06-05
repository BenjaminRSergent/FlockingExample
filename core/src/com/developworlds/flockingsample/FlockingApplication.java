package com.developworlds.flockingsample;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.developworlds.flockingsample.controller.entity.behavior.goals.AvoidPointBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.FlockingBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.MatchHeadingBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.SeperationBehavior;
import com.developworlds.flockingsample.controller.entity.behavior.goals.WanderBehavior;
import com.developworlds.flockingsample.controller.entity.brain.ComplexBrain;
import com.developworlds.flockingsample.controller.entity.locomotion.Locomotion;
import com.developworlds.flockingsample.controller.entity.locomotion.WrappingLocomotion;
import com.developworlds.flockingsample.view.TextureManager;
import com.developworlds.flockingsample.world.World;
import com.developworlds.flockingsample.world.entity.Boid;


public class FlockingApplication extends ApplicationAdapter {
    private SpriteBatch batch;
    private World world;
    private boolean running = false;
    private ComplexBrain brain;
    private Locomotion locomotion;
    private Sprite marker;
    private AvoidPointBehavior avoidBehavior;

    float accumulator = 0;
    final float SEC_PER_FRAME = 1 / 30.0f;
    private OrthographicCamera camera;
    private Vector3 touchPoint = new Vector3();

    @Override
    public void create() {
        batch = new SpriteBatch();

        world = new World(new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        marker = new Sprite(TextureManager.get().getTexture("marker.png"));
        marker.setOrigin(marker.getWidth() / 2, marker.getHeight() / 2);
        marker.setSize(Boid.getBoidSize() * 2, Boid.getBoidSize() * 2);
        ComplexBrain complexBrain = new ComplexBrain();

        avoidBehavior = new AvoidPointBehavior();
        avoidBehavior.setDepartRadius(Boid.getBoidSize() * 10);

        complexBrain.addBehavior(new SeperationBehavior(), 0.5f, 0);
        complexBrain.addBehavior(avoidBehavior, 1.0f, 1);
        complexBrain.addBehavior(new FlockingBehavior(), 0.25f, 2);
        complexBrain.addBehavior(new MatchHeadingBehavior(), 0.25f, 3);
        complexBrain.addBehavior(new WanderBehavior(), 0.5f, 4);

        brain = complexBrain;

        locomotion = new WrappingLocomotion(world.getBounds());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 0);
        camera.update();

        Boid.init();

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



    @Override
    public void render() {
        if (running) {
            accumulator += Gdx.graphics.getDeltaTime();
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        avoidBehavior.setActive(Gdx.input.isTouched());
        if (Gdx.input.isTouched()) {
            camera.unproject(touchPoint);
            marker.setCenter(touchPoint.x, touchPoint.y);
            marker.draw(batch);
        }
        world.draw(batch);
        batch.end();
    }


    public Runnable updater = new Runnable() {
        public void run() {
            while (true) {
                if (Gdx.input.justTouched()) {
                    if (!running) {
                        addBoids(300);
                    }
                    running = true;
                }

                if(Gdx.input.isTouched()) {
                    avoidBehavior.setAntiGoal(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
                    touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 1);
                }

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
