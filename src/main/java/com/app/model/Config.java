package com.app.model;

import java.util.List;

import org.bson.types.ObjectId;

public class Config {

	private ObjectId id;
	private List<String> emails;
	private List<String> tags;
	
	public Config() {
		super();
	}

	public Config(List<String> emails, List<String> tags) {
		super();
		this.emails = emails;
		this.tags = tags;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
}
