package com.stg.vms.model;

import java.util.List;

public class LocationAccessResponse {
	private String name;
	private String visitorType;
	private String photo;
	private boolean allowed;
	private String currentLocation;
	private List<String> allowedLocations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVisitorType() {
		return visitorType;
	}

	public void setVisitorType(String visitorType) {
		this.visitorType = visitorType;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean isAllowed() {
		return allowed;
	}

	public void setAllowed(boolean allowed) {
		this.allowed = allowed;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public List<String> getAllowedLocations() {
		return allowedLocations;
	}

	public void setAllowedLocations(List<String> allowedLocations) {
		this.allowedLocations = allowedLocations;
	}
}
