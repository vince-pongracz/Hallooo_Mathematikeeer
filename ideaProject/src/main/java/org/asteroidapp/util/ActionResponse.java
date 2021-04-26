package org.asteroidapp.util;

import java.util.ArrayList;
import java.util.List;

public class ActionResponse {
    private boolean success;
    private String message;
    private List<String> refreshObjects = new ArrayList<>();
    private List<String> deleteObjects = new ArrayList<>();

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

    public List<String> getRefreshObjects() {
        return refreshObjects;
    }

    public ActionResponse addRefreshObjects(List<String> refreshObjects) {
        this.refreshObjects.addAll(refreshObjects);
        return this;
    }
    public ActionResponse addRefreshObjects(String refreshObject) {
        this.refreshObjects.add(refreshObject);
        return this;
    }

    public List<String> getDeleteObjects() {
        return deleteObjects;
    }

    public ActionResponse addDeleteObjects(List<String> deleteObjects) {
        this.deleteObjects.addAll(deleteObjects);
        return this;
    }
    public ActionResponse addDeleteObjects(String deleteObject) {
        this.deleteObjects.add(deleteObject);
        return this;
    }
}
