package com.mygdx.game;

import java.util.List;

/**
 * Gestiona las colisiones entre objetos del juego.
 */
public class CollisionManager {
    private List<Collidable> collidables;

    /**
     * Constructor para CollisionManager.
     *
     * @param collidables Lista de objetos colisionables.
     */
    public CollisionManager(List<Collidable> collidables) {
        this.collidables = collidables;
    }

    /**
     * Maneja todas las colisiones entre los objetos.
     */
    public void handleCollisions() {
        for (int i = 0; i < collidables.size(); i++) {
            for (int j = i + 1; j < collidables.size(); j++) {
                Collidable obj1 = collidables.get(i);
                Collidable obj2 = collidables.get(j);

                if (isColliding(obj1, obj2)) {
                    obj1.onCollision((GameObject) obj2);
                    obj2.onCollision((GameObject) obj1);
                }
            }
        }
    }

    /**
     * Verifica si dos objetos están colisionando.
     *
     * @param a Primer objeto colisionable.
     * @param b Segundo objeto colisionable.
     * @return Verdadero si están colisionando, falso en caso contrario.
     */
    private boolean isColliding(Collidable a, Collidable b) {
        if (a instanceof GameObject && b instanceof GameObject) {
            GameObject objA = (GameObject) a;
            GameObject objB = (GameObject) b;
            return (objA.getX() < objB.getX() + objB.getWidth() &&
                    objA.getX() + objA.getWidth() > objB.getX() &&
                    objA.getY() < objB.getY() + objB.getHeight() &&
                    objA.getY() + objA.getHeight() > objB.getY());
        }
        return false;
    }
}
