package com.app.model.rss;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "channel")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Channel {

    @JacksonXmlProperty(localName = "title")
    @JacksonXmlCData
    private String title;
    @JacksonXmlProperty(localName = "description")
    @JacksonXmlCData
    private String description;
    @JacksonXmlProperty(localName = "link")
    private String link;
    @JacksonXmlProperty(localName = "generator")
    private String generator;
    @JacksonXmlProperty(localName = "lastBuildDate")
    private String lastBuildDate;
    @JacksonXmlProperty(localName = "link", namespace = "atom")
    private String atom_link;
    @JacksonXmlProperty(localName = "item")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Item> items;
    
    public Channel() {
        super();
    }

    public Channel(String title, String description, String link, String generator, String lastBuildDate, String atom_link,
            List<Item> items) {
        super();
        this.title = title;
        this.description = description;
        this.link = link;
        this.generator = generator;
        this.lastBuildDate = lastBuildDate;
        this.atom_link = atom_link;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getAtomLink() {
        return atom_link;
    }

    public void setAtomLink(String atom) {
        this.atom_link = atom;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((atom_link == null) ? 0 : atom_link.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((generator == null) ? 0 : generator.hashCode());
        result = prime * result + ((items == null) ? 0 : items.hashCode());
        result = prime * result + ((lastBuildDate == null) ? 0 : lastBuildDate.hashCode());
        result = prime * result + ((link == null) ? 0 : link.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
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
        Channel other = (Channel) obj;
        if (atom_link == null) {
            if (other.atom_link != null)
                return false;
        } else if (!atom_link.equals(other.atom_link))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (generator == null) {
            if (other.generator != null)
                return false;
        } else if (!generator.equals(other.generator))
            return false;
        if (items == null) {
            if (other.items != null)
                return false;
        } else if (!items.equals(other.items))
            return false;
        if (lastBuildDate == null) {
            if (other.lastBuildDate != null)
                return false;
        } else if (!lastBuildDate.equals(other.lastBuildDate))
            return false;
        if (link == null) {
            if (other.link != null)
                return false;
        } else if (!link.equals(other.link))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Channel [title=" + title + ", description=" + description + ", link=" + link + ", generator="
                + generator + ", lastBuildDate=" + lastBuildDate + ", atom_link=" + atom_link + ", items=" + items
                + "]";
    }

}
