package com.developworlds.flockingsample.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.entity.Boid;

import java.util.ArrayList;
import java.util.List;

public class World {
    private static final int DEF_CELL_SIZE = Boid.DEF_SIZE * 3;

    class Cell {
        private Vector2 position;
        private int size;
        private List<Boid> boidsInCell = new ArrayList<Boid>(100);

        public Cell(Vector2 position, int size) {
            this.position = position.cpy();
            this.size = size;
        }

        public void add(Boid boid) {
            boidsInCell.add(boid);
        }

        public void remove(Boid boid) {
            int size = boidsInCell.size();
            for (int index = 0; index < size; index++) {
                if (boid == boidsInCell.get(index)) {
                    boidsInCell.remove(index);
                    break;
                }
            }
        }

        public List<Boid> getBoidsInCell() {
            return boidsInCell;
        }
    }

    Cell[][] cells;

    private ArrayList<Boid> boids = new ArrayList<Boid>();
    private Rectangle bounds = new Rectangle();

    public World(Rectangle bounds) {
        this.bounds.set(bounds);

        int xCells = (int) (bounds.width / DEF_CELL_SIZE + 1);
        int yCells = (int) (bounds.height / DEF_CELL_SIZE + 1);

        cells = new Cell[xCells][yCells];

        for (int x = 0; x < xCells; x++) {
            for (int y = 0; y < yCells; y++) {
                cells[x][y] = new Cell(new Vector2(x * DEF_CELL_SIZE, y * DEF_CELL_SIZE), DEF_CELL_SIZE);
            }
        }
    }

    public ArrayList<Boid> getBoidsInRange(Circle circle) {
        ArrayList<Boid> inRange = getBoidsInCellsRange(circle);

        int size = inRange.size();
        for (int boidIndex = 0; boidIndex < size; boidIndex++) {
            Boid boid = inRange.get(boidIndex);
            if (!boid.isInside(circle)) {
                inRange.remove(boidIndex);
                boidIndex--;
                size = inRange.size();
            }
        }

        return inRange;
    }

    ArrayList<Boid> boidList = new ArrayList<Boid>(1000);

    private ArrayList<Boid> getBoidsInCellsRange(Circle circle) {
        int cellRange = (int) (circle.radius / DEF_CELL_SIZE + 1);

        int startX = (int) (circle.x / DEF_CELL_SIZE - cellRange);
        int startY = (int) (circle.y / DEF_CELL_SIZE - cellRange);

        for (int x = Math.max(0, startX); x < startX + 2 * cellRange && x < cells.length; x++) {
            for (int y = Math.max(0, startY); y < startY + 2 * cellRange && y < cells[0].length; y++) {
                List<Boid> boidsInCell = cells[x][y].getBoidsInCell();
                for (int boidIndex = 0; boidIndex < boidsInCell.size(); boidIndex++) {
                    boidList.add(boidsInCell.get(boidIndex));
                }
            }
        }

        return boidList;
    }

    public void draw(SpriteBatch batch) {
        int size = boids.size();
        for (int index = 0; index < size; index++) {
            boids.get(index).draw(batch);
        }
    }


    public void update(float deltaTime) {
        int size = boids.size();
        for (int index = 0; index < size; index++) {
            Boid boid = boids.get(index);
            removeFromCell(boid);
            boid.update(this, deltaTime);
            addToCell(boid);
        }
    }


    private void addToCell(Boid boid) {
        int cellX = (int) (boid.position.x / DEF_CELL_SIZE);
        int cellY = (int) (boid.position.y / DEF_CELL_SIZE);

        cells[cellX][cellY].add(boid);
    }

    private void removeFromCell(Boid boid) {
        int cellX = (int) (boid.position.x / DEF_CELL_SIZE);
        int cellY = (int) (boid.position.y / DEF_CELL_SIZE);

        cells[cellX][cellY].remove(boid);
    }


    public Rectangle getBounds() {
        return bounds;
    }

    public void addBoid(Boid boid) {
        boids.add(boid);

        addToCell(boid);
    }

    public void removeBoid(Boid boid) {
        boids.remove(boid);
    }

    public ArrayList<Boid> getBoids() {
        return boids;
    }
}
