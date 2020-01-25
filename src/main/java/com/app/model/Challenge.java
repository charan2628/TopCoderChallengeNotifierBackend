package com.app.model;

import org.bson.types.ObjectId;

public class Challenge {

	private ObjectId id;
	private String name;
	private String description;
	private String link;
	
	public Challenge(String name, String description, String link) {
		super();
		this.name = name;
		this.description = description;
		this.link = link;
	}

	public ObjectId getId() {
		return id;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
}