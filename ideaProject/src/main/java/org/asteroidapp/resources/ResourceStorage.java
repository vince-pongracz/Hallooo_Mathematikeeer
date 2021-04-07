package org.asteroidapp.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//TODO kellene egy async garbage collector, ami az emptyket eltakarítja, ha bekerülnek...
public class ResourceStorage {
    private List<Resource> resourceList = new ArrayList<>();

    public ResourceStorage() {
    }

    public int getAllCapacity() {
        return allCapacity;
    }

    public void setAllCapacity(int allCapacity) {
        this.allCapacity = Math.abs(allCapacity);
    }

    //It should be set before, because otherwise the pushMore() function won't work properly
    private int allCapacity = 1;

    private boolean storageIsFull() {
        if (allCapacity == (resourceList.size() - countOf(new Empty()))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param whatYouWant
     * @return new Empty or a resource what was asked
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
        return new Empty();
    }

    /**
     * Pop a random resource from storage
     * @note when capacity == 1, then it will return the resource, but you don't have to specify, what you want (workaround for mine situation)
     *
     * @return an Empty, when it's size == 0 is true, or a random resource from collection
     */
    public Resource popRandomResource() {
        Resource result = null;
        //CallStackViewer.getInstance().methodStartsLogCall("popRandomResource (ResourceStorage) called");
        if (resourceList == null || resourceList.size() == 0) {
            //CallStackViewer.getInstance().innerMethodCall("The resource was: Empty");
            result = new Empty();

        } else {
            Collections.shuffle(resourceList);
            result = resourceList.remove(0);
            //CallStackViewer.getInstance().innerMethodCall("The resource was: " + result.getName());
        }

        //CallStackViewer.getInstance().methodReturns();
        return result;
    }

    public boolean pushResource(Resource whatYouWant) {
        if (whatYouWant != null && !this.storageIsFull()) {
            resourceList.add(whatYouWant);
            return true;
        } else {
            //TODO log -- trace?
            return false;
        }
    }

    public List<Resource> getResourceList() {
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
        if (allCapacity - count + countOf(new Empty()) >= resourceList.size()) {
            for (int i = 0; i < count; i++) {
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("----------------------\n");
        builder.append("ResourceStorage:\n");
        builder.append("cap: ").append(this.allCapacity).append("\n");
        builder.append("Uran: ").append(countOf(new Uran())).append("\n");
        builder.append("Coal: ").append(countOf(new Coal())).append("\n");
        builder.append("Frozenwater: ").append(countOf(new FrozenWater())).append("\n");
        builder.append("Iron: ").append(countOf(new Iron())).append("\n");
        builder.append("Empty: ").append(countOf(new Empty())).append("\n");
        builder.append("----------------------\n");
        return builder.toString();
    }


    //TODO improve: add a ResourceStorage to another
}
