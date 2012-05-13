package com.cheapvacations.logic;

public class LogicException extends Exception {

	private static final long serialVersionUID = 100L;

	public LogicException( String message ) {
		super( message );
		System.out.println("Logic Exception: "+message);
	}

}
