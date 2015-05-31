package com.developworlds.flockingsample.world.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.controller.entity.BoidAI;
import com.developworlds.flockingsample.controller.entity.Locomotion;
import com.developworlds.flockingsample.view.TextureManager;
import com.developworlds.flockingsample.world.World;

public class Boid {
    private static final String BOID_TEXTURE = "simple_boid.png";
    private Sprite sprite;

    public Color color = Color.BLUE;
    public Vector2 position = new Vector2();
    public Vector2 goal = new Vector2();
    public Vector2 size = new Vector2(30, 30);

    public float maxSpeed = 100;
    public float currSpeed = maxSpeed;

    private BoidAI boidAi;
    private Locomotion locomotion;
    private float facingRotation;
    private BoidType type = BoidType.Basic;

    public Boid() {
        sprite = new Sprite(TextureManager.get().getTexture(BOID_TEXTURE));
    }

    public void update(World world, float deltaTime) {
        if(boidAi != null) {
            boidAi.update(this, world, deltaTime);
        }
        if (locomotion != null) {
            locomotion.update(this, deltaTime);
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

    public void setBoidAi(BoidAI boidAi) {
        this.boidAi = boidAi;
    }

    public void setLocomotion(Locomotion locomotion) {
        this.locomotion = locomotion;
    }

    public void setFacingRotation(float rotation) {
        facingRotation = rotation;
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
}
