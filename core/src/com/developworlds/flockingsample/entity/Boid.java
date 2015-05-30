package com.developworlds.flockingsample.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.view.TextureManager;

public class Boid {
    private static final String BOID_TEXTURE = "simple_boid.png";
    private Sprite sprite;
    private Vector2 position;
    private Vector2 goal;
    private Vector2 toGoal;
    private float currSpeed;
    private float maxSpeed;

    public Boid() {
        sprite = new Sprite(TextureManager.get().getTexture(BOID_TEXTURE));
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void update() {
        // toGoal is not local to avoid allocation a ton of Vector2's
        toGoal.set(goal);
        toGoal.sub(position);

        currSpeed = Math.min(currSpeed, maxSpeed);
        toGoal.nor().scl(currSpeed);

        setPosition(position.x + toGoal.x, position.y + toGoal.x);
    }

    public Vector2 getGoal() {
        return goal.cpy();
    }

    public void setGoal(Vector2 goal) {
        this.goal.set(goal);
    }

    public Vector2 getPosition() {
        return position.cpy();
    }

    public void setPosition(float x, float y) {
        position.set(x,y);
        sprite.setCenter(x, y);
    }

    public Vector2 getSize() {
        return new Vector2(sprite.getWidth(), sprite.getHeight());
    }

    public void setSize(float width, float height) {
        sprite.setSize(width, height);
    }

    public Color getColor() {
        return sprite.getColor();
    }

    public void setColor(Color color) {
        sprite.setColor(color);
    }
}
