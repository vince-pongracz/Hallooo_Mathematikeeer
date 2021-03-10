package org.asteroidapp.resources;

import java.util.ArrayList;
import java.util.List;

public class ResourceStorage {
    private List<Resource> resourceList = new ArrayList<>();

    public ResourceStorage() { }

    public int getAllCapacity() {
        return allCapacity;
    }

    public void setAllCapacity(int allCapacity) {
        this.allCapacity = Math.abs(allCapacity);
    }

    private int allCapacity = 0;

    private boolean storageIsFull() {
        if (allCapacity == resourceList.size()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param whatYouWant
     * @return null or a resource what was asked
     */
    public Resource popResource(Resource whatYouWant) {
        if (whatYouWant != null) {
            for (var res : resourceList) {
                if (res.equals(whatYouWant)) {
                    var retResource = res;
                    resourceList.remove(retResource);
                    return retResource;
                }
            }
        }
        return null;
    }

    public void pushResource(Resource whatYouWant) {
        if (whatYouWant != null && resourceList.size() <= allCapacity) {
            resourceList.add(whatYouWant);
        } else {
            //TODO log
        }
    }

    private List<Resource> getResourceList() {
        return resourceList;
    }

    public ResourceStorage popMore(int count, Resource typeFrom) {
        //if more asked, than exists
        if (count > this.countOf(typeFrom)) {
            return null;
        } else {
            ResourceStorage returnStorage = new ResourceStorage();
            returnStorage.setAllCapacity(count);

            int i = 0;
            for (var res : resourceList) {
                if (res.equals(typeFrom) && i < count) {
                    returnStorage.pushResource(res);
                    i++;
                }
            }

            if (returnStorage.storageIsFull()) {
                for (var remove : returnStorage.getResourceList()) {
                    resourceList.remove(remove);
                }
            }
            return returnStorage;
        }
    }

    public boolean pushMore(int count, Resource typeFrom) {
        if (allCapacity - count >= resourceList.size()) {
            for (int i = 0; i < count; i++) {
                //TODO kellene copy ctor a resourcenal
                resourceList.add(typeFrom);
            }
            return true;
        } else {
            //cannot add this amount of resource
            return false;
        }
    }

    public int countOf(Resource type) {
        int counter = 0;

        for (var item : resourceList) {
            if (item.equals(type)) {
                ++counter;
            }
        }
        return counter;
    }
}
