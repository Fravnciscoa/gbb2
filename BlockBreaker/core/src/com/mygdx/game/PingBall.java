package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Representa la bola en el juego BlockBreaker.
 */
public class PingBall extends GameObject implements Collidable {
	private float xSpeed;
	private float ySpeed;
	private Color color;
	private boolean estaQuieto;

	/**
	 * Enum para identificar el lado de la colisión.
	 */
	public enum CollisionSide {
		NONE, TOP, BOTTOM, LEFT, RIGHT
	}

	/**
	 * Constructor para la clase PingBall.
	 *
	 * @param x             Posición en el eje X.
	 * @param y             Posición en el eje Y.
	 * @param size          Tamaño de la bola (radio).
	 * @param xSpeed        Velocidad en el eje X.
	 * @param ySpeed        Velocidad en el eje Y.
	 * @param iniciaQuieto  Estado inicial de la bola.
	 */
	public PingBall(float x, float y, int size, float xSpeed, float ySpeed, boolean iniciaQuieto) {
		super(x, y, size * 2, size * 2); // width and height as diameter
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.estaQuieto = iniciaQuieto;
		this.color = Color.WHITE;
	}

	@Override
	public void draw(ShapeRenderer shape) {
		shape.setColor(color);
		shape.circle(x, y, width / 2f);
	}

	/**
	 * Actualiza la posición de la bola y maneja las colisiones con los bordes de la pantalla.
	 */
	public void update() {
		if (estaQuieto) return;

		x += xSpeed;
		y += ySpeed;

		// Colisión con los bordes de la pantalla
		if (x - (width / 2f) < 0) {
			x = width / 2f;
			xSpeed = Math.abs(xSpeed);
			normalizeSpeed();
		} else if (x + (width / 2f) > Gdx.graphics.getWidth()) {
			x = Gdx.graphics.getWidth() - (width / 2f);
			xSpeed = -Math.abs(xSpeed);
			normalizeSpeed();
		}

		if (y + (height / 2f) > Gdx.graphics.getHeight()) {
			y = Gdx.graphics.getHeight() - (height / 2f);
			ySpeed = -Math.abs(ySpeed);
			normalizeSpeed();
		}
	}

	/**
	 * Maneja la colisión con otro objeto del juego.
	 *
	 * @param other Objeto con el que colisiona.
	 */
	@Override
	public void onCollision(GameObject other) {
		if (other instanceof Paddle) {
			// Lógica de colisión con Paddle
			ySpeed = Math.abs(ySpeed);

			Paddle paddle = (Paddle) other;
			float paddleCenter = paddle.getX() + paddle.getWidth() / 2f;
			float distanceFromCenter = (x - paddleCenter) / (paddle.getWidth() / 2f);
			xSpeed += distanceFromCenter * 2;
			normalizeSpeed();
		} else if (other instanceof Block) {
			// Lógica de colisión con Block
			Block block = (Block) other;
			block.setDestroyed(true);

			CollisionSide side = determineCollisionSide(block);
			switch (side) {
				case TOP:
					ySpeed = Math.abs(ySpeed);
					break;
				case BOTTOM:
					ySpeed = -Math.abs(ySpeed);
					break;
				case LEFT:
					xSpeed = -Math.abs(xSpeed);
					break;
				case RIGHT:
					xSpeed = Math.abs(xSpeed);
					break;
				default:
					break;
			}
			normalizeSpeed();
		}
	}

	/**
	 * Determina el lado de la colisión con un bloque.
	 *
	 * @param block Bloque con el que colisiona.
	 * @return Lado de la colisión.
	 */
	private CollisionSide determineCollisionSide(Block block) {
		float overlapLeft = (x + (width / 2f)) - block.getX();
		float overlapRight = (block.getX() + block.getWidth()) - (x - (width / 2f));
		float overlapTop = (block.getY() + block.getHeight()) - (y - (height / 2f));
		float overlapBottom = (y + (height / 2f)) - block.getY();

		float minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

		if (minOverlap == overlapLeft) {
			return CollisionSide.LEFT;
		} else if (minOverlap == overlapRight) {
			return CollisionSide.RIGHT;
		} else if (minOverlap == overlapTop) {
			return CollisionSide.TOP;
		} else {
			return CollisionSide.BOTTOM;
		}
	}

	/**
	 * Normaliza la velocidad de la bola para mantener una velocidad constante.
	 */
	private void normalizeSpeed() {
		float speed = (float) Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed);
		float desiredSpeed = 7.0f; // Ajusta este valor según sea necesario

		if (speed == 0) {
			xSpeed = desiredSpeed * ((Math.random() > 0.5) ? 1 : -1);
			ySpeed = desiredSpeed * ((Math.random() > 0.5) ? 1 : -1);
		} else {
			float factor = desiredSpeed / speed;
			xSpeed *= factor;
			ySpeed *= factor;
		}
	}

	// Métodos getters y setters
	public float getXSpeed() {
		return xSpeed;
	}

	public void setXSpeed(float xSpeed) {
		this.xSpeed = xSpeed;
	}

	public float getYSpeed() {
		return ySpeed;
	}

	public void setYSpeed(float ySpeed) {
		this.ySpeed = ySpeed;
	}

	public boolean isEstaQuieto() {
		return estaQuieto;
	}

	public void setEstaQuieto(boolean estaQuieto) {
		this.estaQuieto = estaQuieto;
	}
}
