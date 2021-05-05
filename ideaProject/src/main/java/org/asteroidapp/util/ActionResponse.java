package org.asteroidapp.util;

public class ActionResponse {
    private boolean success = false;
    private String message = "Default";

    public GameState getGameState() {
        return gameState;
    }

    public ActionResponse setGameState(GameState newState) {
        gameState = newState;
        return this;
    }

    private GameState gameState = GameState.RUNNING;

    public boolean isSuccessful() {
        return success;
    }

    public ActionResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ActionResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public ActionResponse setDefaultMessage() {
        this.message = (success && message.equals("Default")) ? "Successed" : "Invalid or wrong operation";
        return this;
    }
}
