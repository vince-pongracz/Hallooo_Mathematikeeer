package org.asteroidapp.util;


import org.asteroidapp.CommandInterpreter;

import java.util.HashSet;
import java.util.Set;

public class ActionResponse {
    private boolean success;
    private String message;
    private Set<String> refreshObjects = new HashSet<>();
    private Set<String> deleteObjects = new HashSet<>();

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

    public Set<String> getRefreshObjects() {
        return refreshObjects;
    }

    public ActionResponse addRefreshObjects(Set<String> refreshObjects) {
        this.refreshObjects.addAll(refreshObjects);
        return this;
    }
    public ActionResponse addRefreshObjects(String refreshObject) {
        this.refreshObjects.add(refreshObject);
        return this;
    }

    public Set<String> getDeleteObjects() {
        return deleteObjects;
    }

    public ActionResponse addDeleteObjects(Set<String> deleteObjects) {
        this.deleteObjects.addAll(deleteObjects);
        return this;
    }
    public ActionResponse addDeleteObjects(String deleteObject) {
        this.deleteObjects.add(deleteObject);
        return this;
    }

    public ActionResponse setDefaultMessage(){
        this.message = success ? "Successed" : "Invalid or wrong operation";
        return this;
    }
}
