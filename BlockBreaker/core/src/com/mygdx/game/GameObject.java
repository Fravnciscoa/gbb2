package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Clase abstracta que representa un objeto del juego.
 */
public abstract class GameObject {
    protected float x;
    protected float y;
    protected int width;
    protected int height;

    /**
     * Constructor para GameObject.
     *
     * @param x      Posición en el eje X.
     * @param y      Posición en el eje Y.
     * @param width  Ancho del objeto.
     * @param height Alto del objeto.
     */
    public GameObject(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Método abstracto para dibujar el objeto.
     *
     * @param shape ShapeRenderer para dibujar el objeto.
     */
    public abstract void draw(ShapeRenderer shape);

    // Métodos getters y setters
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
