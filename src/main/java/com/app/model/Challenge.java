package com.app.model;

import org.bson.types.ObjectId;

public class Challenge {

    private ObjectId id;
    private String name;
    private String description;
    private String link;
    
    public Challenge() {
        super();
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((link == null) ? 0 : link.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Challenge other = (Challenge) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (link == null) {
            if (other.link != null)
                return false;
        } else if (!link.equals(other.link))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Challenge [id=" + this.id + ", name=" + this.name + ", description=" + this.description + ", link="
                + this.link + "]";
    }
    
}