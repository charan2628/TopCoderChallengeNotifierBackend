package com.app.model.rss;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JacksonXmlRootElement(localName = "guid")
public class Guid {

	@JacksonXmlProperty(isAttribute = true)
	private String isPermaLink;
	
	@JacksonXmlText
	private String value;

	public Guid() {
		super();
	}
	
	public Guid(String isPermaLink, String value) {
		super();
		this.isPermaLink = isPermaLink;
		this.value = value;
	}

	public String getIsPermaLink() {
		return isPermaLink;
	}

	public void setIsPermaLink(String isPermaLink) {
		this.isPermaLink = isPermaLink;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isPermaLink == null) ? 0 : isPermaLink.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Guid other = (Guid) obj;
		if (isPermaLink == null) {
			if (other.isPermaLink != null)
				return false;
		} else if (!isPermaLink.equals(other.isPermaLink))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Guid [isPermaLink=" + isPermaLink + ", value=" + value + "]";
	}
	
}
