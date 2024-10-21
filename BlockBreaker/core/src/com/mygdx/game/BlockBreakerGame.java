package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color; // Importación añadida
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase principal del juego BlockBreaker.
 */
public class BlockBreakerGame extends ApplicationAdapter {
	private OrthographicCamera camera;
	private ShapeRenderer shape;

	private PingBall ball;
	private Paddle pad;
	private ArrayList<Block> blocks;
	private int vidas;
	private int nivel;
	private CollisionManager collisionManager;
	private ScoreManager scoreManager;

	private List<Collidable> collidables;

	// Estados del juego
	private boolean isPaused = false;
	private boolean isGameOver = false;

	// Botones
	private Button pauseButton;
	private Button restartButton;

	@Override
	public void create () {
		// Inicialización de la cámara
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// Inicialización de ShapeRenderer
		shape = new ShapeRenderer();

		// Inicialización del puntaje
		scoreManager = new ScoreManager();

		// Inicialización de niveles, vidas y bloques
		nivel = 1;
		vidas = 3;
		blocks = new ArrayList<>();
		crearBloques(nivel + 1); // Crear bloques basados en el nivel

		// Inicialización de la paleta y la bola
		pad = new Paddle(Gdx.graphics.getWidth() / 2f - 50, 40, 100, 10);
		ball = new PingBall(pad.getX() + pad.getWidth() / 2f - 5, pad.getY() + pad.getHeight() + 11, 10, 5, 7, true);

		// Inicialización de la lista de colisionables y CollisionManager
		collidables = new ArrayList<>();
		collidables.add(ball);
		collidables.add(pad);
		collidables.addAll(blocks);
		collisionManager = new CollisionManager(collidables);

		// Inicialización de los botones
		pauseButton = new Button(700, 430, 80, 40, Color.GRAY, "Pausa");
		restartButton = new Button(700, 380, 80, 40, Color.GRAY, "Reiniciar");
	}

	/**
	 * Crea bloques en filas basadas en el número de filas especificado.
	 *
	 * @param filas Número de filas de bloques.
	 */
	private void crearBloques(int filas) {
		blocks.clear();
		collidables.removeAll(blocks);
		int blockWidth = 70;
		int blockHeight = 26;
		int y = Gdx.graphics.getHeight() - 50; // Posicionar bloques un poco debajo del borde superior
		for (int cont = 0; cont < filas; cont++) {
			y -= blockHeight + 10;
			for (int x = 5; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
				Block block = new Block(x, y, blockWidth, blockHeight);
				blocks.add(block);
				collidables.add(block);
			}
		}
	}

	/**
	 * Dibuja los textos de puntaje, vidas y nivel.
	 */
	private void dibujaTextos() {
		camera.update();
		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		// No es ideal dibujar texto con ShapeRenderer. Se recomienda usar SpriteBatch y BitmapFont.
		// Para simplificar, omitiremos la implementación detallada del texto.
		shape.end();
	}

	@Override
	public void render () {
		// Limpiar la pantalla
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		// Dibujar los botones
		shape.begin(ShapeRenderer.ShapeType.Filled);
		pauseButton.draw(shape);
		restartButton.draw(shape);
		shape.end();

		// Manejo de entradas para botones
		handleButtonInput();

		if (!isPaused && !isGameOver) {
			shape.begin(ShapeRenderer.ShapeType.Filled);

			// Dibujar la paleta
			pad.draw(shape);

			// Manejo de la bola en reposo
			if (ball.isEstaQuieto()) {
				ball.setX(pad.getX() + pad.getWidth() / 2f - ball.getWidth() / 2f);
				ball.setY(pad.getY() + pad.getHeight() + 11);
				if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
					ball.setEstaQuieto(false);
				}
			} else {
				ball.update();
			}

			// Verificar si la bola cae por debajo
			if (ball.getY() < 0) {
				vidas--;
				scoreManager.resetScore();
				resetLevel();
			}

			// Verificar Game Over
			if (vidas <= 0) {
				isGameOver = true;
				resetLevel();
			}

			// Verificar si el nivel se completó
			if (blocks.isEmpty()) {
				nivel++;
				crearBloques(nivel + 1);
				ball = new PingBall(pad.getX() + pad.getWidth() / 2f - 5, pad.getY() + pad.getHeight() + 11, 10, 5, 7, true);
				collidables.add(ball);
			}

			// Manejar colisiones
			collisionManager.handleCollisions();

			// Actualizar puntaje y eliminar bloques destruidos
			for (int i = 0; i < blocks.size(); i++) {
				Block b = blocks.get(i);
				if (b.isDestroyed()) {
					scoreManager.addScore(10); // Incrementa el puntaje por cada bloque destruido
					collidables.remove(b);
					blocks.remove(b);
					i--; // Ajustar índice tras eliminación
				}
			}

			// Dibujar la bola y los bloques
			ball.draw(shape);
			for (Block b : blocks) {
				b.draw(shape);
			}

			shape.end();
		}

		// Dibujar textos de puntaje, vidas y nivel
		dibujaTextos();

		// Manejo de estados especiales
		if (isPaused) {
			drawPauseScreen();
		}

		if (isGameOver) {
			drawGameOverScreen();
		}
	}

	/**
	 * Maneja la entrada de los botones de pausa y reinicio.
	 */
	private void handleButtonInput() {
		if (Gdx.input.justTouched()) {
			float touchX = Gdx.input.getX();
			float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

			if (pauseButton.isClicked(touchX, touchY)) {
				isPaused = !isPaused;
			}

			if (restartButton.isClicked(touchX, touchY)) {
				restartGame();
			}
		}
	}

	/**
	 * Dibuja la pantalla de pausa.
	 */
	private void drawPauseScreen() {
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.setColor(0, 0, 0, 0.5f); // Semi-transparente negro
		shape.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shape.end();

		// Dibujar texto de "PAUSADO" usando SpriteBatch y BitmapFont
		// Implementación simplificada
		// Requiere inicializar SpriteBatch y BitmapFont si se desea mostrar texto
	}

	/**
	 * Dibuja la pantalla de Game Over.
	 */
	private void drawGameOverScreen() {
		shape.begin(ShapeRenderer.ShapeType.Filled);
		shape.setColor(0, 0, 0, 0.7f); // Más opaco
		shape.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shape.end();

		// Dibujar texto de "GAME OVER" y puntaje final usando SpriteBatch y BitmapFont
		// Implementación simplificada
		// Requiere inicializar SpriteBatch y BitmapFont si se desea mostrar texto
	}

	/**
	 * Reinicia el juego a su estado inicial.
	 */
	private void restartGame() {
		vidas = 3;
		nivel = 1;
		scoreManager.resetScore();
		resetLevel();
		isGameOver = false;
		isPaused = false;
	}

	/**
	 * Resetea el nivel actual.
	 */
	private void resetLevel() {
		crearBloques(nivel + 1);
		ball = new PingBall(pad.getX() + pad.getWidth() / 2f - 5, pad.getY() + pad.getHeight() + 11, 10, 5, 7, true);
		collidables.clear();
		collidables.add(ball);
		collidables.add(pad);
		collidables.addAll(blocks);
		collisionManager = new CollisionManager(collidables);
	}

	@Override
	public void dispose () {
		shape.dispose();
		// Dispose de otros recursos como SpriteBatch y BitmapFont si se usan
	}
}
