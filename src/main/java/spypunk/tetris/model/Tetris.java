/*
 * Copyright © 2016 spypunk <spypunk@gmail.com>
 *
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package spypunk.tetris.model;

import java.awt.Point;
import java.util.Map;
import java.util.Optional;

public class Tetris {

    public static class Builder {

        private final Tetris tetris = new Tetris();

        private Builder() {
        }

        public static Builder instance() {
            return new Builder();
        }

        public Builder setBlocks(Map<Point, Optional<Block>> blocks) {
            tetris.setBlocks(blocks);
            return this;
        }

        public Builder setNextShape(Shape nextShape) {
            tetris.setNextShape(nextShape);
            return this;
        }

        public Builder setShapesStatistics(Map<ShapeType, Integer> shapesStatistics) {
            tetris.setShapesStatistics(shapesStatistics);
            return this;
        }

        public Tetris build() {
            return tetris;
        }

    }

    private static final int INITIAL_SPEED = 1000;

    private Map<Point, Optional<Block>> blocks;

    private Shape currentShape;

    private Shape nextShape;

    private boolean over;

    private long lastMoveTime;

    private long lastLockedTime;

    private Optional<Movement> movement;

    private Map<ShapeType, Integer> shapesStatistics;

    private int level;

    private int score;

    private int completedRows;

    private int speed = INITIAL_SPEED;

    private boolean paused;

    public Map<Point, Optional<Block>> getBlocks() {
        return blocks;
    }

    public void setBlocks(Map<Point, Optional<Block>> blocks) {
        this.blocks = blocks;
    }

    public Shape getCurrentShape() {
        return currentShape;
    }

    public void setCurrentShape(Shape currentShape) {
        this.currentShape = currentShape;
    }

    public Shape getNextShape() {
        return nextShape;
    }

    public void setNextShape(Shape nextShape) {
        this.nextShape = nextShape;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public long getLastMoveTime() {
        return lastMoveTime;
    }

    public void setLastMoveTime(long lastMoveTime) {
        this.lastMoveTime = lastMoveTime;
    }

    public Optional<Movement> getMovement() {
        return movement;
    }

    public void setMovement(Optional<Movement> movement) {
        this.movement = movement;
    }

    public Map<ShapeType, Integer> getShapesStatistics() {
        return shapesStatistics;
    }

    public void setShapesStatistics(Map<ShapeType, Integer> shapesStatistics) {
        this.shapesStatistics = shapesStatistics;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCompletedRows() {
        return completedRows;
    }

    public void setCompletedRows(int completedRows) {
        this.completedRows = completedRows;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public long getLastLockedTime() {
        return lastLockedTime;
    }

    public void setLastLockedTime(long lastLockedTime) {
        this.lastLockedTime = lastLockedTime;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
