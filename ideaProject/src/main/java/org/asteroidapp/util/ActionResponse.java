package org.asteroidapp.util;

public class ActionResponse {
    private boolean success;
    private String message = "Default";

    public boolean isWin() {
        return win;
    }

    public ActionResponse setWin(boolean win) {
        this.win = win;
        return this;
    }

    private boolean win = false;

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

    public ActionResponse setDefaultMessage(){
        this.message = success ? "Successed" : "Invalid or wrong operation";
        return this;
    }
}
