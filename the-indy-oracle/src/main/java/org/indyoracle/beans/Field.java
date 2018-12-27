package org.indyoracle.beans;

public class Field {
	private String label;
	private String name;
	private String type;
	private String placeholder;
	private String required;

	public Field(String label, String name, String type, String placeholder, String required) {
		this.label       = label;
		this.name        = name;
		this.type        = type;
		this.placeholder = placeholder;
		this.required    = required;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getPlaceholder() {
		return placeholder;
	}
	
	public void setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
	}
	
	public String getRequired() {
		return required;
	}
	
	public void setRequired(String required) {
		this.required = required;
	}
}
