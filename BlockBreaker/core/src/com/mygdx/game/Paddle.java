package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Representa la paleta controlada por el jugador.
 */
public class Paddle extends GameObject implements Collidable {

    /**
     * Constructor para la clase Paddle.
     *
     * @param x      Posición en el eje X.
     * @param y      Posición en el eje Y.
     * @param width  Ancho de la paleta.
     * @param height Alto de la paleta.
     */
    public Paddle(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(ShapeRenderer shape) {
        shape.setColor(Color.BLUE);
        float deltaX = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) deltaX = -15;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) deltaX = 15;

        if (x + deltaX >= 0 && x + deltaX + width <= Gdx.graphics.getWidth()) {
            x += deltaX;
        }
        shape.rect(x, y, width, height);
    }

    /**
     * Maneja la colisión con otro objeto del juego.
     *
     * @param other Objeto con el que colisiona.
     */
    @Override
    public void onCollision(GameObject other) {
        if (other instanceof PingBall) {
            // Lógica específica para la paleta al colisionar con la bola
            // Por ejemplo, cambiar el color de la paleta temporalmente
            // o ajustar la velocidad de la bola
            // Aquí puedes implementar cualquier lógica deseada
        }
    }

    // Métodos específicos de Paddle si es necesario
}
