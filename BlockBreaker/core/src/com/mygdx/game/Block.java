package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

/**
 * Representa un bloque en el juego BlockBreaker.
 */
public class Block extends GameObject implements Collidable {
    private Color color;
    private boolean destroyed;

    /**
     * Constructor para la clase Block.
     *
     * @param x      Posición en el eje X.
     * @param y      Posición en el eje Y.
     * @param width  Ancho del bloque.
     * @param height Alto del bloque.
     */
    public Block(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.destroyed = false;
        Random random = new Random((int) (x + y));
        this.color = new Color(0.1f + random.nextFloat(), random.nextFloat(), random.nextFloat(), 1.0f);
    }

    @Override
    public void draw(ShapeRenderer shape) {
        if (!destroyed) {
            shape.setColor(color);
            shape.rect(x, y, width, height);
        }
    }

    /**
     * Maneja la colisión con otro objeto del juego.
     *
     * @param other Objeto con el que colisiona.
     */
    @Override
    public void onCollision(GameObject other) {
        if (other instanceof PingBall) {
            this.destroyed = true;
            // Aquí podrías incrementar el puntaje o manejar otras lógicas
        }
    }

    // Getters y Setters
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}
