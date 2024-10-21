package com.mygdx.game;

/**
 * Gestiona el puntaje del jugador.
 */
public class ScoreManager {
    private int currentScore;
    private int highScore;

    /**
     * Constructor para ScoreManager.
     */
    public ScoreManager() {
        this.currentScore = 0;
        this.highScore = 0;
    }

    /**
     * Incrementa el puntaje actual.
     *
     * @param points Puntos a añadir.
     */
    public void addScore(int points) {
        currentScore += points;
        if (currentScore > highScore) {
            highScore = currentScore;
        }
    }

    /**
     * Reinicia el puntaje actual.
     */
    public void resetScore() {
        currentScore = 0;
    }

    // Métodos getters
    public int getCurrentScore() {
        return currentScore;
    }

    public int getHighScore() {
        return highScore;
    }
}
