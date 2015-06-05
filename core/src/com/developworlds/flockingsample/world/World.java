package com.developworlds.flockingsample.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.developworlds.flockingsample.world.entity.Boid;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class World {
    private final int defaultCellSize;

    class Cell {
        private List<Boid> boidsInCell = new LinkedList<Boid>();

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

        Boid.setBoidSize((int) (bounds.getWidth() / 100));
        defaultCellSize = Boid.getBoidSize() * 3;

        int xCells = (int) (bounds.width / defaultCellSize + 1);
        int yCells = (int) (bounds.height / defaultCellSize + 1);

        cells = new Cell[xCells][yCells];

        for (int x = 0; x < xCells; x++) {
            for (int y = 0; y < yCells; y++) {
                cells[x][y] = new Cell();
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
                size--;
            }
        }

        return inRange;
    }

    ArrayList<Boid> boidList = new ArrayList<Boid>(1000);

    private ArrayList<Boid> getBoidsInCellsRange(Circle circle) {
        boidList.clear();
        int cellRange = (int) (circle.radius / defaultCellSize + 1);

        int startX = (int) (circle.x / defaultCellSize - cellRange);
        int startY = (int) (circle.y / defaultCellSize - cellRange);

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
            Cell startCell = getCellAtPosition(boid.position);
            boid.update(this, deltaTime);
            Cell endCell = getCellAtPosition(boid.position);

            if (startCell == null && endCell == null) {
                continue;
            }

            if (startCell != null && endCell == null) {
                startCell.remove(boid);
            } else if (startCell == null && endCell != null) {
                endCell.add(boid);
            } else if (!startCell.equals(endCell)) {
                startCell.remove(boid);
                endCell.add(boid);
            }
        }
    }


    private void addToCell(Boid boid) {
        Cell cell = getCellAtPosition(boid.position);

        if (cell != null) {
            cell.add(boid);
        }
    }

    private void removeFromCell(Boid boid) {
        Cell cell = getCellAtPosition(boid.position);

        if (cell != null) {
            cell.remove(boid);
        }
    }

    private Cell getCellAtPosition(Vector2 position) {
        int cellX = (int) (position.x / defaultCellSize);
        int cellY = (int) (position.y / defaultCellSize);

        if (cellX > 0 && cellX < cells.length
                && cellY > 0 && cellY < cells[0].length) {
            return cells[cellX][cellY];
        }

        return null;
    }


    public Rectangle getBounds() {
        return bounds;
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
