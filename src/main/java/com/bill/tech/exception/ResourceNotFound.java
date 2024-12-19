package com.bill.tech.exception;

public class ResourceNotFound extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3545842558215195360L;
	private final String resource;
	private final String fieldname;
	private final String fieldvalue;

	public ResourceNotFound(String resource, String fieldname, String fieldvalue) {
		super(String.format("%s not found for %s with %s ", resource, fieldname, fieldvalue));
		this.resource = resource;
		this.fieldname = fieldname;
		this.fieldvalue = fieldvalue;
	}


}
