package com.dcai.password.service.model;

public interface AttributeCollection<E extends Enum<E>> {
	public void putAttribute(E field, String value);
	public Object getAttribute(E field);
}