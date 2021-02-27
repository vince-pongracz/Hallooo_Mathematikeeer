package org.asteroidapp;

import java.util.*;

/**
 * 
 */
public class Core {

	/**
	 * Default constructor
	 */
	public Core(Resource initResource) {
		resource = new ArrayList<>();
		resource.add(initResource);
	}
	public Core(int capacity){
		this.capacity = capacity;
		resource = new ArrayList<>();
	}

	/**
	 * 
	 */
	private int capacity = 1;

	/**
	 *
	 */
	private List<Resource> resource;


	/**
	 * @return
	 */
	public List<Resource> getResource() {
		List<Resource> returnList = new ArrayList<>();
		returnList.addAll(resource);
		resource.clear();
		return returnList;
	}

	/**
	 * @param newResource
	 */
	//TODO: esetek lefedése
	public void setResource(Resource newResource) {
		if(capacity == 1 && resource.isEmpty()){
			resource.add(newResource);
		}else if (capacity >1 && resource.size() < capacity){
			resource.add(newResource);
		}else{

		}
	}

}