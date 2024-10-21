package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Representa un botón en la interfaz del juego.
 */
public class Button {
    private float x;
    private float y;
    private float width;
    private float height;
    private Color color;
    private String label;

    private BitmapFont font;
    private SpriteBatch batch;

    /**
     * Constructor para la clase Button.
     *
     * @param x      Posición en el eje X.
     * @param y      Posición en el eje Y.
     * @param width  Ancho del botón.
     * @param height Alto del botón.
     * @param color  Color del botón.
     * @param label  Texto del botón.
     */
    public Button(float x, float y, float width, float height, Color color, String label) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.label = label;

        this.font = new BitmapFont();
        this.batch = new SpriteBatch();
    }

    /**
     * Dibuja el botón.
     *
     * @param shape ShapeRenderer para dibujar el botón.
     */
    public void draw(ShapeRenderer shape) {
        shape.setColor(color);
        shape.rect(x, y, width, height);

        // Dibujar el texto del botón
        batch.begin();
        font.setColor(Color.BLACK);
        font.draw(batch, label, x + (width / 2f) - (font.getData().scaleX * label.length() * 2), y + (height / 2f) + 5);
        batch.end();
    }

    /**
     * Verifica si el botón ha sido clickeado.
     *
     * @param touchX Coordenada X del clic.
     * @param touchY Coordenada Y del clic.
     * @return Verdadero si el botón fue clickeado, falso en caso contrario.
     */
    public boolean isClicked(float touchX, float touchY) {
        return touchX >= x && touchX <= x + width &&
                touchY >= y && touchY <= y + height;
    }

    // Getters y Setters si son necesarios
}
