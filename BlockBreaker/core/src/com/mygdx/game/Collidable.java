package com.mygdx.game;

/**
 * Interfaz que define el comportamiento de colisión.
 */
public interface Collidable {
    /**
     * Método que maneja la lógica de colisión con otro objeto.
     *
     * @param other Objeto con el que colisiona.
     */
    void onCollision(GameObject other);
}
