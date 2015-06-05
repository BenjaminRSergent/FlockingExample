package com.developworlds.flockingsample.world.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.controller.entity.brain.BoidAI;
import com.developworlds.flockingsample.controller.entity.locomotion.Locomotion;
import com.developworlds.flockingsample.utility.LowPassFilter;
import com.developworlds.flockingsample.view.TextureManager;
import com.developworlds.flockingsample.world.World;


public class Boid {
    private static final String TAG = Boid.class.getSimpleName();

    private static final String BOID_TEXTURE = "simple_boid.png";
    private static final float MIN_FACING_VELO = 1;

    private static int boidSize;
    private Sprite sprite;

    public Color color = Color.BLUE;
    public Vector2 position = new Vector2();
    public Vector2 velocity = new Vector2();
    public Vector2 acceleration = new Vector2();
    public Vector2 size = new Vector2(getBoidSize(), getBoidSize());

    public float maxSpeed = 250;
    public float maxAcceleration = maxSpeed * 2;

    private BoidAI boidAi;
    private Locomotion locomotion;
    private float facingRotation;
    private BoidType type = BoidType.Basic;
    private LowPassFilter facingSmoother = new LowPassFilter(0.1f);

    public Boid() {
        sprite = new Sprite(TextureManager.get().getTexture(BOID_TEXTURE));
    }

    public void update(World world, float deltaTime) {
        if (boidAi != null) {
            boidAi.update(this, world, deltaTime);
        }
        if (locomotion != null) {
            locomotion.update(this, world, deltaTime);
        }

        if (velocity.len2() > MIN_FACING_VELO) {
            // The image is rotated 90. We add 270 because negative numbers
            // disrupt our smoothing.
            facingSmoother.addValue(velocity);
            facingRotation = facingSmoother.get().angle();
        }
    }

    public void draw(SpriteBatch batch) {
        sprite.setCenter(position.x, position.y);
        sprite.setSize(size.x, size.y);
        sprite.setColor(color);
        sprite.setOrigin(size.x / 2, size.y / 2);
        sprite.setRotation(getFacingRotation());
        sprite.draw(batch);
    }


    public static int getBoidSize() {
        return boidSize;
    }

    public static void setBoidSize(int boidSize) {
        Boid.boidSize = boidSize;
    }

    public void setBoidAi(BoidAI boidAi) {
        this.boidAi = boidAi;
    }

    public void setLocomotion(Locomotion locomotion) {
        this.locomotion = locomotion;
    }

    public float getFacingRotation() {
        return facingRotation;
    }

    public boolean isInside(Circle circle) {
        return circle.contains(position.x, position.y);
    }

    public BoidType getType() {
        return type;
    }

    public float getRadiusSq() {
        return (float) Math.pow(getRadius(), 2.0f);
    }

    public float getRadius() {
        return Math.max(size.x, size.y);
    }

    public static void init() {
        TextureManager.get().getTexture(BOID_TEXTURE); // Ensure texture is loaded on gl thread
    }
}
